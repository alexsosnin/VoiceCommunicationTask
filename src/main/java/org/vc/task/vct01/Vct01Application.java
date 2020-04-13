package org.vc.task.vct01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.vc.task.vct01.db.config.DbConfig;

@SpringBootApplication
@Import (DbConfig.class)
public class Vct01Application {

	public static void main(String[] args) {
		SpringApplication.run(Vct01Application.class, args);
	}

}
