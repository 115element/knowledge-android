package com.example.knowledge_android.comparator.sign;


public class SignTest {

    public void testSign() {
        // 设置私钥与公�?
        String privateKey = "XXXXXXXXXXXXXXXXXXXXXXXXxxwggJcAgEAAoGBAK/iA/sED2vlrjIMgdsai0Ih1FqLA1c2pcxlgWpijLwFYRHBnS4zuBcs3cAZCCoYiL2IDAdkaXPkbSVArsLC9s5KpCwV2lx0rjHNmJ4PTs6350iDoYQtBUH6e21xev/aYi3oSqTw5PD7bYNaS1P3fs92DaxUsENyXVgKDV8fND/fAgMBAAECgYEAkQaFj7pWWPqnOz+S566oe1xXtF0B4Sz/Y12ja6xdLUAmPTTvvUQKHJnzM02wrL1UKyv99y51wYjt8pS2RYPraugSmWCBwbWBDzwdmYIYqpESIMU8hcJb8hZ+KMvKMXlXtlpihdIgJGmS7Ncmes6rxzlWb0JL6oaMy60wdbDIDwECQQDv13vld1SV9shgHErqBfiHKzCsKlafnquqV2UZeSli3EFWioi9ElNksBSTsOwTVnekWccIov08BmyBGUIZp4OBAkEAu7txOFXelAnXqNceBwueLqp9cPQLUi+F7x6JMviKuV3DNkrWO+BXfy3/ulnWG+dRsEJErL/KoKrJ2kruF7fzXwJAQqCb/Iz/5IEf/QMfFJAAEJzA8rL4SXNswO9yWHc+NgZ16DKn9c5HeGsp7DVz/5M/vVprXkzZCHnIXrzDjhjpAQJAEZ0rqVJ9OLyXrHLKkXSomc5LSL7NFR/6XA4sZ0jkkzHV8BEjeqi47Oje9pgZO7L/eh8tnFu+YkbD+6ROJJGQ1wJAFws2Isd1OYsgTGatWdJGnFoGVh2EUW9YA//zj2P6kVDEG+ncTfTiP9EuomaI2yXXBR1W8EhZvMRYc4P3JPCvcw==";
        String publicKey = "BBBBBBBBBBBBBBBBBBBBBotCIdRaiwNXNqXMZYFqYoy8BWERwZ0uM7gXLN3AGQgqGIi9iAwHZGlz5G0lQK7CwvbOSqQsFdpcdK4xzZieD07Ot+dIg6GELQVB+nttcXr/2mIt6Eqk8OTw+22DWktT937Pdg2sVLBDcl1YCg1fHzQ/3wIDAQAB";
        System.out.println("1024私钥，用于签名：" + privateKey);
        System.out.println("1024公钥，用于验签：" + publicKey);
        // 带签名内�?
        String content = "just test，我是测试内�?";
        System.out.println("带签名内容：" + content);
        try {
            // 调用SDK进行签名
            String sign = SignUtil.rsaSign(content, privateKey);
            System.out.println("使用私钥对原文进行签名得到：" + sign);
            // 调用SDK进行验签
            boolean checkSignResult = SignUtil.rsaCheck(content, sign, publicKey);
            System.out.println("使用公钥对签名进行验签，结果为：" + checkSignResult);
            //Assert.assertTrue(checkSignResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
