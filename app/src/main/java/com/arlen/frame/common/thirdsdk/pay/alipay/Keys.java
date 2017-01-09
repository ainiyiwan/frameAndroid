/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.arlen.frame.common.thirdsdk.pay.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088701358025481";
	//收款支付宝账号
	public static final String DEFAULT_SELLER = "2088701358025481";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKumJs4LX/60nuFoPzbivJO8qvaGxTAowv20Vq6eb7C0TFSD86y42YpIt9spsq4TUjaib0SuX+BhOM8AEjyMg7+BYEW7itMRH/SZh6fWlOdh++Lj3JYG/HNo35WvS7W4e2PKO9axoF7OCxHUHTS+vgOWrEhW8PCzQYdRhCO81Ej9AgMBAAECgYBSmAWOGb0cQ288CTx5vYp+EEe3jkvuC38gMxmOtqAJyAf+luu2zc+nkaweYicfSh7COVEPHFT4E/AQ8vjTd82gm2oLfdNk8LB0sIGgj7+xDOM3dkO5v0NH6fVrxGn3RbRnYz62qtH0fBgfXsJSKx0XH/qyogYvrpHGFxOLc11npQJBAOGS1Zmc/2kHFtS5sznvis1wo2BTNjQa/i3YCCJqkcHsDu0dI/XNvkyzgqfNd2squNCe4W0je4vk2+SBEOQujucCQQDCzUbDslUGGZXw+tr9Ea5nlaDNv+DTj/fnTFB2xsJ4E5av4sOS+epanUnvmYhN9KQJyC7q9wQQe1u5kT9TSWB7AkEAjVCw0kCB3elcCz6fIn+IBHY5U+Ithe0XKd86EV2UHNnm6/MikR4oxuJ6yuc0lgW4rLGsZfQhtR1WCMw2qczlJwJAdQUtclfJfZ2XFikLriSV+UHIVHDklRKSIE8WsxNZNRrHVadKRxt8Laz5Akbfu7yvYfTwQlPbDC+efU4ElXqpywJADKzpqaMugJu2sA3yQ33vuWtzdGdn98M32VYy0Lk7K8V935RGKWdw7tHjT+VdLqsTMZ6/befYH+I832JlYf+tsQ==";
	
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
}
