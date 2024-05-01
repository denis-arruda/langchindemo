package com.github.denisarruda.langchindemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.denisarruda.langchindemo.dto.MyQuestion;

import dev.langchain4j.model.chat.ChatLanguageModel;

@RestController
public class OpenAIController {

//	@Value("${OPENAI_API_KEY}")
//	private String apiKey;
	
	@Autowired
	private ChatLanguageModel chatModel;
	
	@PostMapping("/answer")
	public String chatWithOpenAI(@RequestBody MyQuestion question) {
		return chatModel.generate(question.question());
	}
}