package com.github.denisarruda.langchindemo.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.denisarruda.langchindemo.dto.MyQuestion;
import com.github.denisarruda.langchindemo.dto.MyStructuredTemplate.PromptDeReceita;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;

@RestController
public class OpenAIController {

	@Autowired
	private ChatLanguageModel chatModel;

	@Autowired
	private RAGConfiguration ragConfig;

	private Assistant assistant;

	@PostMapping("/answer")
	public String chatWithOpenAI(@RequestBody MyQuestion question) {
		return chatModel.generate(question.question());
	}

	@GetMapping("/receita")
	public String facaUmaReceita() {
		PromptDeReceita rcPrompt = new PromptDeReceita();
		rcPrompt.prato = "Cozido";
		rcPrompt.ingredientes = Arrays.asList("ovo", "tomate");

		Prompt prompt = StructuredPromptProcessor.toPrompt(rcPrompt);

		return chatModel.generate(prompt.text());
	}

	@PostMapping("/ragchat")
	public String chatWithRag(@RequestBody MyQuestion myMessage) {
		try {
			if (assistant == null) {
				assistant = ragConfig.configure();
			}
			return assistant.answer(myMessage.question());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}