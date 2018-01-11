package config;



import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import functions.Function.Input;

public class Handler extends SpringBootRequestHandler<Input, Boolean> {
}