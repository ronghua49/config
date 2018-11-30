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
	
	@GetMapping("/model/download")
	public void modelDownload(HttpServletResponse resp) throws NotFoundException, IOException {

		File file = new File("D:\\excel\\模板.xls");
		if (file.exists()) {
			resp.setContentType("application/octet-stream");
			// 添加响应头 传入前端
			resp.addHeader("Content-Disposition",
					"attachment; filename=" + new String("模板".getBytes("GBK"), "ISO8859-1") + ".xls");
			InputStream in = new FileInputStream(file);
			OutputStream out = resp.getOutputStream();

			IOUtils.copy(in, out);
			out.flush();
			out.close();
			in.close();

		} else {
			throw new FileNotFoundException("路资源不存在");
		}

	}

	// excel 导入数据库
	@PostMapping("/excel/import")
	@ResponseBody
	public  JsonResponse excelImport(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException {
		if (!file.isEmpty()) {
			
			String fileName = file.getOriginalFilename();

			String extension = FilenameUtils.getExtension(fileName);
			
		
			String savePath = request.getSession().getServletContext().getRealPath("\\temp");
			File targetPackage = new File(savePath);
			if (!targetPackage.exists()) {
				targetPackage.mkdirs();
			}
			File targetFile = new File(targetPackage,fileName);
			
			InputStream in = file.getInputStream();
			OutputStream out = new FileOutputStream(targetFile);
			
			IOUtils.copy(in, out);
			out.flush();
			out.close();
			in.close();
			
			
			if ("xls".equals(extension)) {
				List<List<Object>> read2003Excel = ExcelImportUtil.read2003Excel(targetFile);
				
				//得到对应值
				List<Movie> movieList=transExcelDataToMovie(read2003Excel);
				movieService.movieBatchInsert(movieList);
			
				return JsonResponse.success("导入成功");
				
			} else if("xlsm".equals(extension)){
				List<List<Object>> read2007Excel = ExcelImportUtil.read2007Excel(targetFile);
				List<Movie> movieList=transExcelDataToMovie(read2007Excel);
				movieService.movieBatchInsert(movieList);
				return JsonResponse.success("导入成功");
				
			}else {
				
				return JsonResponse.fail("请选择正确的文件格式");
			}

		}
		return JsonResponse.fail("请选择上传文件！");
			
	}

	/**
	 * 把excel数据转换为movie
	 * @param read2003Excel
	 * @return movielist
	 */
	private List<Movie> transExcelDataToMovie(List<List<Object>> read2003Excel) {
		ArrayList<Movie> movieList = new ArrayList<>();
		for(int j=0;j<read2003Excel.size();j++) {
			if(j==0) {
				continue;
			}
			Movie movie = new Movie();
			List<Object> row = read2003Excel.get(j);
			
			for(int i=0;i<row.size();i++) {
				if(row.get(i)==null) {
					continue;
				}
				if(i==0) {
					movie.setMovieName((String) row.get(i));
				}else if(i==1) {
					movie.setDirectorName((String) row.get(i));
				}else if(i==2) {
					movie.setArea((String) row.get(i));
				}else if (i==3){
					movie.setContent((String) row.get(i));
				}else if (i==4){
					movie.setScanNum(Integer.valueOf((String) row.get(i)) );
				}else{
					movie.setReplyNum(Integer.valueOf((String) row.get(i)));
				}
				
			}
			movieList.add(movie);
			
		}
		return movieList;
	}

	
}
