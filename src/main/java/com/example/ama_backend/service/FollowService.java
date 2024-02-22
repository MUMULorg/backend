package com.example.ama_backend.service;
import com.example.ama_backend.entity.Follow;
import com.example.ama_backend.entity.UserEntity;
import com.example.ama_backend.persistence.FollowRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    // 내가 팔로잉 중인 유저엔터티 다 불러오기
    public List<Follow> getAllFollowings(final UserEntity fromUser) {
        return followRepository.findByFromUser(fromUser);
    }

    // 나를 팔로우 하는 유저엔터티 다 불러오기
    public List<Follow> getAllFollowers(final UserEntity toUser) {
        return followRepository.findByToUser(toUser);
    }

    // 해당 스페이스의 팔로우 여부
    public Optional<Follow> followOrNot(final UserEntity fromUser, final UserEntity toUser){
       return followRepository.findByFromUserAndToUser(fromUser,toUser);
    }

    // 팔로우하기
    public void follow(final UserEntity fromUser,final UserEntity toUser){
        Follow follow=new Follow(null,fromUser,toUser);
        followRepository.save(follow);
    }

    // 언팔로우하기
    public void deleteFollow(final UserEntity fromUser, final UserEntity toUser) {
        followRepository.deleteByFromUserAndToUser(fromUser, toUser);
    }
}
