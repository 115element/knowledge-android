package com.example.knowledge_android.bigdecimal;

import static com.example.knowledge_android.bigdecimal.BigDecimalConstructors.bd;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Modified from: https://code.google.com/p/bignumutils/
 * <p>
 * Example:
 * <pre>
 * import java.math.BigDecimal;
 *
 * import static hyi.mobilepos.util.BigDecimalConstructors.bd;
 * import static hyi.mobilepos.util.BigDecimalOperator.$;
 * import static hyi.mobilepos.util.BigDecimalOperator.加;
 * import static hyi.mobilepos.util.BigDecimalOperator.乘;
 *
 * public class Test {
 *     public static void main(String[] args) {
 *         // 会先乘除后加减
 *         BigDecimal f = $(1.34, 加, 2.2);
 *         System.out.println(f.toString());
 *         BigDecimal f2 = $(bd(1.34), 加, bd(2.2), 乘, bd(3));
 *         System.out.println(f2.toString());
 *     }
 * }
 * </pre>
 */

public enum BigDecimalOperator {
    PLUS(2) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.add(b);
        }
    },
    加(2) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.add(b);
        }
    },
    MINUS(2) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.subtract(b);
        }
    },
    減(2) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.subtract(b);
        }
    },
    减(2) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.subtract(b);
        }
    },
    TIMES(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.multiply(b);
        }
    },
    乘(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.multiply(b);
        }
    },
    DIV(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.divide(b, MathContext.DECIMAL128);
        }
    },
    除(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.divide(b, MathContext.DECIMAL128);
        }
    },
    REM(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.remainder(b);
        }
    },
    除于(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.remainder(b);
        }
    },
    除餘(3) {
        @Override
        public BigDecimal execute(BigDecimal a, BigDecimal b) {
            return a.remainder(b);
        }
    };

    public abstract BigDecimal execute(BigDecimal a, BigDecimal b);

    public final int precedence;

    private BigDecimalOperator(int precedence) {
        this.precedence = precedence;
    }

    boolean hasGreaterOrSamePrecedenceThan(BigDecimalOperator... operators) {
        for (BigDecimalOperator anotherOperator : operators) {
            if (anotherOperator.precedence > this.precedence) {
                return false;
            }
        }
        return true;
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return a.max(b);
    }

    public static BigDecimal max(BigDecimal a, BigDecimal... others) {
        BigDecimal max = a;
        for (BigDecimal b : others) {
            max = max.max(b);
        }
        return max;
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return a.min(b);
    }

    public static BigDecimal min(BigDecimal a, BigDecimal... others) {
        BigDecimal min = a;
        for (BigDecimal b : others) {
            min = min.min(b);
        }
        return min;
    }

    public static BigDecimal neg(BigDecimal a) {
        return a.negate();
    }

    public static BigDecimal $(BigDecimal a, BigDecimalOperator op,
            BigDecimal b) {
        return op.execute(a, b);
    }

    public static BigDecimal $(int a, BigDecimalOperator op,
            int b) {
        return op.execute(bd(a), bd(b));
    }

    public static BigDecimal $(double a, BigDecimalOperator op,
            double b) {
        return op.execute(bd(a), bd(b));
    }

    public static BigDecimal $(int a, BigDecimalOperator op,
            BigDecimal b) {
        return op.execute(bd(a), b);
    }

    public static BigDecimal $(BigDecimal a, BigDecimalOperator op,
            int b) {
        return op.execute(a, bd(b));
    }

    public static BigDecimal $(BigDecimal bd0, BigDecimalOperator op0,
            BigDecimal bd1, BigDecimalOperator op1, BigDecimal bd2) {
        if (op0.precedence >= op1.precedence) {
            return $($(bd0, op0, bd1), op1, bd2);
        }
        return $(bd0, op0, $(bd1, op1, bd2));
    }

    public static BigDecimal $(BigDecimal bd0, BigDecimalOperator op0,
            BigDecimal bd1, BigDecimalOperator op1, BigDecimal bd2,
            BigDecimalOperator op2, BigDecimal bd3) {
        if (op0.hasGreaterOrSamePrecedenceThan(op1, op2)) {
            return $($(bd0, op0, bd1), op1, bd2, op2, bd3);
        }
        if (op1.hasGreaterOrSamePrecedenceThan(op0, op2)) {
            return $(bd0, op0, $(bd1, op1, bd2), op2, bd3);
        }
        return $(bd0, op0, bd1, op1, $(bd2, op2, bd3));
    }

    public static BigDecimal $(BigDecimal bd0, BigDecimalOperator op0,
            BigDecimal bd1, BigDecimalOperator op1, BigDecimal bd2,
            BigDecimalOperator op2, BigDecimal bd3, BigDecimalOperator op3,
            BigDecimal bd4) {
        if (op0.hasGreaterOrSamePrecedenceThan(op1, op2, op3)) {
            return $($(bd0, op0, bd1), op1, bd2, op2, bd3, op3, bd4);
        }
        if (op1.hasGreaterOrSamePrecedenceThan(op0, op2, op3)) {
            return $(bd0, op0, $(bd1, op1, bd2), op2, bd3, op3, bd4);
        }
        if (op2.hasGreaterOrSamePrecedenceThan(op0, op1, op3)) {
            return $(bd0, op0, bd1, op1, $(bd2, op2, bd3), op3, bd4);
        }
        return $(bd0, op0, bd1, op1, bd2, op2, $(bd3, op3, bd4));
    }

    public static BigDecimal $(BigDecimal bd0, BigDecimalOperator op0,
            BigDecimal bd1, BigDecimalOperator op1, BigDecimal bd2,
            BigDecimalOperator op2, BigDecimal bd3, BigDecimalOperator op3,
            BigDecimal bd4, BigDecimalOperator op4, BigDecimal bd5) {

        if (op0.hasGreaterOrSamePrecedenceThan(op1, op2, op3, op4)) {
            return $($(bd0, op0, bd1), op1, bd2, op2, bd3, op3, bd4, op4, bd5);
        }
        if (op1.hasGreaterOrSamePrecedenceThan(op0, op2, op3, op4)) {
            return $(bd0, op0, $(bd1, op1, bd2), op2, bd3, op3, bd4, op4, bd5);
        }
        if (op2.hasGreaterOrSamePrecedenceThan(op0, op1, op3, op4)) {
            return $(bd0, op0, bd1, op1, $(bd2, op2, bd3), op3, bd4, op4, bd5);
        }
        if (op3.hasGreaterOrSamePrecedenceThan(op0, op1, op2, op4)) {
            return $(bd0, op0, bd1, op1, bd2, op2, $(bd3, op3, bd4), op4, bd5);
        }
        return $(bd0, op0, bd1, op1, bd2, op2, bd3, op3, $(bd4, op4, bd5));
    }

    public static BigDecimal prod(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public static BigDecimal prod(BigDecimal a, BigDecimal... others) {
        BigDecimal total = a;
        for (BigDecimal b : others) {
            total = total.multiply(b);
        }
        return total;
    }

    public static int sgn(BigDecimal a) {
        return a.signum();
    }

    public static BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public static BigDecimal sum(BigDecimal a, BigDecimal... others) {
        BigDecimal total = a;
        for (BigDecimal b : others) {
            total = total.add(b);
        }
        return total;
    }

    public static BigDecimal abs(BigDecimal a) {
        return a.abs();
    }

}
