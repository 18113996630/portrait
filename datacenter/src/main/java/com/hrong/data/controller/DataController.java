package com.hrong.data.controller;

import com.hrong.data.entity.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author hrong
 * @ClassName DataController
 * @Description
 * @Date 2019/5/25 16:09
 **/
@Slf4j
@RestController
@RequestMapping(value = "/data")
public class DataController {

	@GetMapping()
	public Object hello(HttpServletRequest request) {
		String addr = request.getRemoteUser();
		log.info("data:{}", addr);
		return ResultVo.success(addr);
	}

	@PostMapping(value = "/log")
	public Object receiveLog(String data) {
		return data;
	}

}
