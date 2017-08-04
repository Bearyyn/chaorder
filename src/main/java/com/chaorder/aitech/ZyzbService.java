package com.chaorder.aitech;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chaorder.aitech.mapping.ZyzbMapper;
import com.chaorder.aitech.pojo.Zyzb;

@Controller
public class ZyzbService {

	@Autowired  //自动注入mapper接口  
    private ZyzbMapper zyzbMapper;  

	public void setZyzbMapper(ZyzbMapper zyzbMapper) {
		this.zyzbMapper = zyzbMapper;
	}

	public List<Zyzb> FindAllProduct () {
		return zyzbMapper.selectTpdycpmc();
	}
}
