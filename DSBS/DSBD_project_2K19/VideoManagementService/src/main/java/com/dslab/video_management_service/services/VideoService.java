package com.dslab.video_management_service.services;

import com.dslab.video_management_service.entity.Video;
import com.dslab.video_management_service.entity.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VideoService {
    @Autowired
    VideoRepository repository;

    public Iterable<Video> getAll(){
        return repository.findAll();
    }

    public Optional<Video> getVideo(Integer id){ return repository.findById(id); }

    public Video uploadVideo(Video video){
        return repository.save(video);
    }
}

