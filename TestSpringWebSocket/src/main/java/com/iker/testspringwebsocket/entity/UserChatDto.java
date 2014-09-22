package com.iker.testspringwebsocket.entity;

public class UserChatDto {
    private String name;
    private String chatContent;
    private String sessionId;

	@Override
    public String toString() {
        return "UserChatDto{" +
                "name='" + name + '\'' +
                ", chatContent='" + chatContent + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }

    public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getChatContent() {
		return chatContent;
	}



	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}



	public String getSessionId() {
		return sessionId;
	}



	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}




}
