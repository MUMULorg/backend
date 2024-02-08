package com.example.ama_backend.controller;

import com.example.ama_backend.dto.AnswerDTO;
import com.example.ama_backend.dto.ResponseDTO;
import com.example.ama_backend.entity.AnswerEntity;
import com.example.ama_backend.entity.QuestionEntity;
import com.example.ama_backend.entity.SpaceEntity;
import com.example.ama_backend.entity.UserEntity;
import com.example.ama_backend.persistence.AnswerRepository;
import com.example.ama_backend.persistence.QuestionRepository;
import com.example.ama_backend.persistence.SpaceRepository;
import com.example.ama_backend.persistence.UserRepository;
import com.example.ama_backend.service.MailService;
import com.example.ama_backend.service.QAService;
import com.example.ama_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/spaces")
@Slf4j
@CrossOrigin(origins = "https://mumul.site")
public class AnswerController {
    @Autowired
    private QAService qaService;
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    // 답변 등록 API
    @PostMapping("/{spaceId}/{questionId}/answer/create")
    @CrossOrigin(origins = "https://mumul.site")
    public ResponseEntity<?> createAnswer(@PathVariable Long questionId, @PathVariable Long spaceId, @RequestBody AnswerDTO answerDTO) {
        UserEntity spaceUser = userRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        org.springframework.security.core.Authentication testAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if (testAuthentication == null || testAuthentication.getPrincipal() == "anonymousUser") {
            return ResponseEntity.ok().body(false);
        } else {    // 현재 로그인한 유저와 스페이스 유저가 같은 경우에만 답변 작성 가능
            long luser = Long.valueOf((String) testAuthentication.getPrincipal());
            UserEntity currentUser = userService.getUser(luser);
            if(currentUser.getId() == spaceUser.getId()) {
                // 답변달 질문 엔터티
                QuestionEntity question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid question id"));

                // AnswerEntity로 변환
                AnswerEntity answerEntity = AnswerDTO.toEntity(answerDTO);

                // id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
                answerEntity.setId(null);

                // 서비스를 이용해 질문 엔티티를 생성한다
                List<AnswerEntity> entities = qaService.saveAnswer(answerEntity);

                question.setAnswers(entities);

                // 자바 스트림을 이용해 리턴된 엔티티 리스트를  QuestionDTO 로 변환한다.
                List<AnswerDTO> dtos = entities.stream().map(AnswerDTO::new).collect(Collectors.toList());

                // 변환된 QuestionDTO 리스트를 이용해 ResponseDTO 를 초기화한다.
                ResponseDTO<AnswerDTO> responseDTO = ResponseDTO.<AnswerDTO>builder().data(dtos).build();

                Optional<UserEntity> questionUser = userRepository.findById(question.getSendingUserId());
                if (questionUser.isPresent()) {
                    UserEntity qUser = questionUser.get();
                    if (qUser.isAlertSpace()) {
                        String mailTop = "당신의 질문에 답변이 생성되었습니다.";
                        String toAddress = qUser.getEmail();
                        String mailContent = spaceUser.getName() + "님이 회원님의 스페이스에 답변을 생성하였습니다.";

                        mailService.mailSend(toAddress, mailTop, mailContent);
                    }
                }
                // ResponseDTO 를 리턴한다.
                return ResponseEntity.ok().body(responseDTO);
            }
        }
        return ResponseEntity.ok().body(false);
    }

    // 내가 보낸 답변 삭제 API
    // 1. 이동한 스페이스에서(내 스페이스여야 할 필요 없음)
    // 2. 내가 작성한 답변이여야 함
    @DeleteMapping("{spaceId}/{answerId}/{userId}/answer/delete")
    @CrossOrigin(origins = "https://mumul.site")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long userId, @PathVariable Long answerId, @PathVariable Long spaceId) {
        try {
            org.springframework.security.core.Authentication testAuthentication = SecurityContextHolder.getContext().getAuthentication();

            System.out.println("spaceId는 "+ spaceId+ "answerId는 "+ answerId+ "userId는 "+ userId);

            if (testAuthentication == null || testAuthentication.getPrincipal() == "anonymousUser") {
                System.out.println("👽👽👽👽👽👽👽👽👽👽👽👽 /********* 로그인 안해서 false return");
                return ResponseEntity.ok().body(false);
            } else {
                long luser = Long.valueOf((String) testAuthentication.getPrincipal());
                UserEntity currentUser = userService.getUser(luser);

                // 이동한 스페이스 엔터티
                SpaceEntity space = spaceRepository.findById(spaceId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid space id"));

                qaService.deleteAnswer(answerId, currentUser.getId());

                System.out.println("888888888888888888888888888888888888내가 작성한 답변을 삭제했습니다.88888888888888888888888888888888888888");
                return ResponseEntity.ok().body("내가 작성한 답변을 삭제했습니다.");
            }
        } catch (Exception e) {
            // 혹시 예외가 있으면 dto 대신 error 에 메시지를 넣어 리턴한다
            String err = e.getMessage();
            ResponseDTO<AnswerDTO> responseDTO = ResponseDTO.<AnswerDTO>builder().error(err).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
