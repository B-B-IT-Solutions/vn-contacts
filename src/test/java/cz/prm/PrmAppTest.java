package cz.prm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@ExtendWith(MockitoExtension.class)
class PrmAppTest {

  @Mock
  private ConfigurableApplicationContext context;

  @Test
  void newInstance() {
    var app = new PrmApp();
    assertThat(app).isNotNull();
  }

  @Test
  void mainTest() {
    try (MockedStatic<SpringApplication> springApp = Mockito.mockStatic(SpringApplication.class)) {
      var args = new String[]{"arg1", "arg2", "arg3"};
      springApp.when(() -> SpringApplication.run(PrmApp.class, args)).thenReturn(context);
      PrmApp.main(args);
      springApp.verify(() -> SpringApplication.run(PrmApp.class, args));
    }
  }

}