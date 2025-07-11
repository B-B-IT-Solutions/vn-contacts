package cz.prm.utils;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoUtils {

    public static RetrunParamAnswer returnParamAnswer(int paramIndex) {
        return new RetrunParamAnswer(paramIndex);
    }

    public static class RetrunParamAnswer implements Answer {

        private final int paramIndex;

        public RetrunParamAnswer(int paramIndex) {
            this.paramIndex = paramIndex;
        }

        @Override
        public Object answer(InvocationOnMock invocation) {
            return invocation.getArgument(paramIndex);
        }
    }
}
