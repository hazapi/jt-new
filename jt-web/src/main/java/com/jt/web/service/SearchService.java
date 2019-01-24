package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.SolrResult;

public interface SearchService {

	List<SolrResult> search(String q);

}
