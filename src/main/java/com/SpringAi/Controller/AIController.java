package com.SpringAi.Controller;

import java.util.Map;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class AIController {

	@Autowired
	private OllamaChatModel ollama;

	public AIController(OllamaChatModel ollama) {
		super();
		this.ollama = ollama;
	}
		
	@GetMapping("Ai/Response")
	public Flux<String> AiResponse(@RequestParam("message") String message) {
		return ollama.stream(message);
	}

	

	@GetMapping("Ai/call")
	public Flux<ChatResponse> getMessage(@RequestParam("message") String message) {
		Prompt prompt = new Prompt(new UserMessage(message));

		return ollama.stream(prompt);

	}

	@GetMapping("/ai/generate")
	public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return Map.of("generation", ollama.call(message));
	}

}
