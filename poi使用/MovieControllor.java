package com.haohua.controfllor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.haohua.entity.Movie;
import com.haohua.entity.Param;
import com.haohua.entity.Type;
import com.haohua.service.MovieService;
import com.haohua.util.ExcelExportUtil;
import com.haohua.util.JsonResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MovieControllor {
	@Autowired
	private MovieService movieService;

	

	// @RequestMapping(method=RequestMethod.GET,value="/movie")
	@GetMapping("/movie")
	public String showMovieList(Model model) {
		List<Movie> movieLlist = movieService.findAllMovie();
		model.addAttribute("movieList", movieLlist);
		return "movie";
	}

	@GetMapping("/movie/home")
	@ResponseBody
	public JsonResponse movieTableData(@RequestParam(required = false) String param, @RequestParam Integer limit,
			@RequestParam Integer pageNo) {
		try {
			Param param2 = new Gson().fromJson(param, Param.class);
			PageInfo<Movie> moviePageInfo = movieService.findMovieData(param2, pageNo, limit);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("rows", moviePageInfo.getList());
			dataMap.put("total", moviePageInfo.getTotal());
			return JsonResponse.success(dataMap);
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			log.error(stackTrace);
			return JsonResponse.fail();
		}

		
	}

	@GetMapping("/movie/new")
	public String newMovie(Model model) {
		return "movie/new";
	}

	@PostMapping("/movie/save")
	@ResponseBody
	public JsonResponse saveMovie(Movie movie, Integer[] type) {
		try {
			movieService.addNewMovie(movie, type);
			return JsonResponse.success();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.fail();
		}
	}

	@PostMapping("/img/uploader")
	@ResponseBody
	public JsonResponse upload(MultipartFile file) {
		try {
			String fileName = movieService.upload(file);
			return JsonResponse.success(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.fail();
		}
	}
	
	@GetMapping("/pic/download")
	public void download(@RequestParam String picName,HttpServletResponse response) {
		movieService.download(picName,response);
	}
	
	
	
	@GetMapping("/pro/upload")
	public String proUploadView() {
		return "uploadPro";
	}
	
	
	
	@GetMapping("/pro/list")
	public String proList(Model model) {
		model.addAttribute("product", movieService.findProByName("pro1"));
		return "list";
		
	}
	
	@PostMapping("/pro/upload")
	public String proUpload( @RequestParam String proName,@RequestParam String proPrice,@RequestParam String proDescribe,@RequestParam String[] imgNames,RedirectAttributes redirectAttributes) {
		System.out.println(proName);
		System.out.println(proPrice);
		System.out.println(proDescribe);
		for(String name:imgNames) {
			System.out.println(name);
		}
		
	
			
		
		if(proName!=null&&proPrice!=null&&imgNames!=null) {
			
			
			movieService.saveProduct(proName,proPrice,imgNames,proDescribe);
			
			
			redirectAttributes.addFlashAttribute("message", "商品添加成功！");
			return "redirect:/pro/list";
		}else {
			redirectAttributes.addFlashAttribute("message", "商品添加失败！");
			return "redirect:/pro/upload";
		}
		
		
	} 
	
	
	

	@GetMapping("/type/list")
	@ResponseBody
	public JsonResponse FindTypeList() {
		try {
			List<Type> typeList = movieService.findAllTypeList();
			return JsonResponse.success(typeList);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.fail();
		}
	}

	@GetMapping("/movie/test")
	@ResponseBody
	public JsonResponse test() {
		Movie movie = new Movie();
		movie.setArea("china");
		movie.setDirectorName("xitele");
		JsonResponse fall = JsonResponse.fail();
		return fall;
	}

	@GetMapping("/request/test")
	@ResponseBody
	public Map<String, Object> getRequestTest(Model model, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String remoteAddr = request.getRemoteAddr();
		String requestURI = request.getRequestURI();
		StringBuffer requestURL = request.getRequestURL();
		HashMap<String, Object> map = new HashMap<>();
		map.put("requestURI", requestURI);
		map.put("requestURL", requestURL);
		map.put("remoteAddr", remoteAddr);
		map.put("servletPath", servletPath);
		map.put("contextPath", contextPath);
		return map;
	}

	@GetMapping("/movie/export")
	public void exportExcel(@RequestParam(required=false) String param, HttpServletResponse response) {
		Gson gson = new Gson();
		Param queryParam = gson.fromJson(param,Param.class );
		List<List<String>> data = new ArrayList<List<String>>();
		List<Movie> moiveData = movieService.findMoByParam(queryParam);
			for(int i=0;i<moiveData.size();i++) {
				List<String> br = new ArrayList<>();
				Movie movie = moiveData.get(i);
				
				br.add(movie.getMovieName());
				br.add(movie.getDirectorName());
				br.add(movie.getArea());
				br.add(movie.getYear());
				br.add(movie.getSimpleContent());
				br.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movie.getCreateTime()));
				br.add(String.valueOf(movie.getScanNum()));
				br.add(String.valueOf(movie.getReplyNum()));
				data.add(br);
			}
			String[] tableName = {"电影名","导演","区域","年份","简介","创建时间","浏览数","回复数"};
		
			ExcelExportUtil.download(response, "电影列表", "电影列表", tableName, data);
	}

	@GetMapping("/insert/batch")
	@ResponseBody
	public int sqlInsertBatch() {

		Movie movie = new Movie();

		movie.setArea("伊拉克");
		movie.setContent("测试1");

		Movie movie2 = new Movie();

		movie2.setArea("伊朗");
		movie2.setContent("测试2");

		List<Movie> insertList = Lists.newArrayList(movie, movie2);
		try {

			movieService.movieBatchInsert(insertList);
		} catch (Exception e) {
			ExceptionUtils.getStackFrames(e);
			
		}
		return 0;

	}
	
	@GetMapping("/echarts")
	public String echarts() {
		return "echars";
		
	}
	
}
