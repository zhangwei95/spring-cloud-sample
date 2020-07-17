package com.zhangwei95.service;


import com.zhangwei95.entity.Three;
import com.zhangwei95.mapper.ThreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yueyi2019
 */
@Service
public class RmThreeService {
	@Autowired
    ThreeMapper mapper;
	
	public String rm3() {
		
		Three o = new Three();
		o.setId(3);
		o.setName("rm3");
		mapper.insertSelective(o);
		
		return "";
	}
}
