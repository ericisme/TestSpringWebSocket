package com.iker.testspringwebsocket.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iker.testspringwebsocket.entity.UserChatDto;
import com.iker.testspringwebsocket.util.LimitQueue;




@Controller
public class ChatController {	
	
	/**
	 * 聊天记录缓存
	 */
	private LimitQueue<UserChatDto> userChatqueue = new LimitQueue<UserChatDto>(10);
	
	
    /**
     * 用于转发数据(sendTo)
     */
	@Autowired
    private SimpMessagingTemplate template;    
    
	/**
	 * index
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/")
    public String index(ModelMap model) {
        return "index";
    }

    /**
     * WebSocket聊天的相应接收方法和转发方法
     * @param userChatDto
     */
	@MessageMapping("/userChat")
    public void userChat(UserChatDto userChatDto) {
        //找到需要发送的地址
        String dest = "/userChat/chat" + "1";
        //发送用户的聊天记录
        this.template.convertAndSend(dest, userChatDto);
        //获取缓存，并将用户最新的聊天记录存储到缓存中  
        try {  
        	userChatDto.setName(URLDecoder.decode(userChatDto.getName(), "utf-8"));  
        	userChatDto.setChatContent(URLDecoder.decode(userChatDto.getChatContent(), "utf-8"));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        userChatqueue.offer(userChatDto);
    }
	/**
     * 初始化，初始化聊天记录
     *
     * @param coordinationId 协同空间的id
     */
    @SubscribeMapping("/init/userChatqueue")
    public Map<String,Object> init() {
        System.out.println("------------新用户进入，空间初始化---------");
        Map<String, Object> document = new HashMap<String, Object>();
        document.put("chat",userChatqueue);        
        return document;
    }
}
