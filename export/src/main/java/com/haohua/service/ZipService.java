package com.haohua.service;

import java.io.File;
import java.util.List;

public interface ZipService {
    void zipFiles(List<String> fileList, File zipFile);
}
