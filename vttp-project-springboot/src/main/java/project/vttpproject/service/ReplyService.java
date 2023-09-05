package project.vttpproject.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.replies.Reply;
import project.vttpproject.repository.ReplyRepository;

@Service
public class ReplyService {
    
    @Autowired
    private ReplyRepository replyRepo;

    public Optional<Reply> getReplyById(String id) throws UpdateException{
        return replyRepo.getReplyById(id);
    }

    public List<Reply> getRepliesByReviewId(String reviewId) throws UpdateException{
        return replyRepo.getRepliesByReviewId(reviewId);
    }
    
    public List<Reply> getRepliesByUserId(Integer userId) throws UpdateException{
        return replyRepo.getRepliesByUserId(userId);
    }

    public String createNewReply(Reply reply) throws UpdateException{
        reply.setId(UUID.randomUUID().toString());
        return replyRepo.saveReply(reply);
    }

}
