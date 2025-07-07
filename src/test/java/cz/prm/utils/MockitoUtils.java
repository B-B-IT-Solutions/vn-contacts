package cz.prm.utils;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoUtils {

    public static RetrunParamAnswer returnParamAnswer(int paramIndex) {
        return new RetrunParamAnswer(paramIndex);
    }

    public static class RetrunParamAnswer implements Answer {

        private final int PARAM_INDEX;

        public RetrunParamAnswer(int paramIndex) {
            this.PARAM_INDEX = paramIndex;
        }

        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            return invocation.getArgument(PARAM_INDEX);
        }
    }
}
