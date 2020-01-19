package com.dslab.video_processing_service.controller;

import com.dslab.video_processing_service.Executor.ScriptExecutor;
import com.dslab.video_processing_service.entity.Video;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

@Controller
@RequestMapping(path="/videos")
public class ScriptController {

    //POST http://localhost:8090/process
    @PostMapping(path="/process")
    public @ResponseBody ResponseEntity<String> scriptExecution(@RequestBody Video request) throws JSONException, InterruptedException {
        String id = request.getId_video();
        ScriptExecutor script = new ScriptExecutor(id);
        Thread exe = new Thread(script);
        exe.start();
        exe.join();
        return new ResponseEntity<String>("Video processed!", HttpStatus.OK);
    }
}
