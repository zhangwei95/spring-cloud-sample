package com.zhangwei95.service;


import com.zhangwei95.entity.One;
import com.zhangwei95.mapper.OneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yueyi2019
 */
@Service
public class RmOneService {
	
	@Autowired
    OneMapper mapper;
	
	public String rm1() {
		
		
		rm2();
		rm3();
		
		One o = new One();
		o.setId(1);
		o.setName("rm1");
		mapper.insertSelective(o);
		int i = 1/0;
		
		return "";
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	private void rm2() {
		restTemplate.getForEntity("http://seata-two/test/rm2", null);
	}
	
	private void rm3() {
		restTemplate.getForEntity("http://seata-three/test/rm3", null);
	}
}
