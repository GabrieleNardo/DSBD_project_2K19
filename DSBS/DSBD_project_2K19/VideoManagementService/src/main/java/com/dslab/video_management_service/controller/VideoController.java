package com.dslab.video_management_service.controller;

import com.dslab.video_management_service.entity.Video;
import com.dslab.video_management_service.services.VideoService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

@Controller
@RequestMapping(path="/videos")
public class VideoController {

    @Autowired
    VideoService service_video;

    // POST http://localhost:8080/videos Auth necessaria
    @PostMapping(path="")
    public @ResponseBody Video addVideos(Authentication auth,@RequestBody  Video video){
        video.setId_user(auth.getName());
        video.setState("new");
        return service_video.uploadVideo(video);
    }

    // POST http://localhost:8080/videos/:id Auth necessaria
    @PostMapping(path ="/{id}")
    public @ResponseBody ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file, Authentication auth, @PathVariable Integer id) throws JSONException {
        Video myVideo = service_video.getVideo(id).get();
        if((service_video.getVideo(id).isPresent()) && (myVideo.getId_user().equals(auth.getName()))){
            try {
                byte[] bytes = file.getBytes();
                // Creating the directory to store file
                String rootPath = "/app/var";
                File dir = new File("/app/var/video/" + id);
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator +  "video.mp4");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                System.out.println("Server File Location=" + serverFile.getAbsolutePath());
            } catch (Exception e) {
            }

            //Post on Video Processing Service
            String postUrl = "http://videoprocessingservice:8090/videos/process";
            //create RestTemplate, headers and set contentType to application JSon
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //create a video JSon object and set the id_video field as the request id
            JSONObject videoObject = new JSONObject();
            videoObject.put("id_video", id);
            //request
            HttpEntity<String> request = new HttpEntity<String>(videoObject.toString(), headers);
            //post to the Video Processing Service
            ResponseEntity<String> result = restTemplate.postForEntity(postUrl, request, String.class);

            myVideo.setState("Uploaded");
            service_video.uploadVideo(myVideo);
            return new ResponseEntity<String>("Video Upload",HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    // GET http://localhost:8080/videos
    @GetMapping(path="")
    public @ResponseBody Iterable<Video> getAllVideos(){
        return service_video.getAll();
    }

    // GET http://localhost:8080/videos/:id
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Void> getVideo(@PathVariable Integer id){
        if(!service_video.getVideo(id).isPresent()){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            // istruzione per il redirect
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/var/videofiles/"+id+"/video.mpd"));
            /*String  url = "/app/var/videofiles/"+id+"/video.mpd";
            headers.add("Location",url);*/
            return new ResponseEntity<>(headers,HttpStatus.MOVED_PERMANENTLY);
        }
    }
}
