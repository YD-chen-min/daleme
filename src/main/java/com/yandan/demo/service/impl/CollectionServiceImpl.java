package com.yandan.demo.service.impl;

import com.yandan.demo.VO.CollectInfoVO;
import com.yandan.demo.config.MyConfig;
import com.yandan.demo.converter.Converter;
import com.yandan.demo.dataobject.CollectInfo;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.CollectForm;
import com.yandan.demo.repository.CollectRepository;
import com.yandan.demo.repository.DepartmentRepository;
import com.yandan.demo.repository.LocationRepository;
import com.yandan.demo.repository.UserRepository;
import com.yandan.demo.service.CollectionService;
import com.yandan.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/14  20:40
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MyConfig myConfig;
    @Transactional
    @Override
    public CollectInfoVO addCollection(CollectForm collectForm) throws ApiException {
        if(collectRepository.findByCollectName(collectForm.getName())!=null) throw new ApiException(ErrorEnum.COLLECTION_ALREADY_EXIST);
        int n=myConfig.getCollectionNumber();
        String key=KeyUtil.getKey(n);
        while (exists(key)){
            key=KeyUtil.getKey(n);
        }
        CollectInfo collectInfo= Converter.collectForm2CollectInfo(collectForm,key);
        collectInfo=collectRepository.save(collectInfo);
        CollectInfoVO collectInfoVO=collectInfo2CollectInfoVO(collectInfo);
        return collectInfoVO ;
    }

    @Override
    public List<CollectInfoVO> getCollectionList() {
        List<CollectInfo> collectInfoList=collectRepository.findAll();
        List<CollectInfoVO> collectInfoVOList=new ArrayList<CollectInfoVO>();
        if (collectInfoList==null) return collectInfoVOList;
        for (CollectInfo collectInfo:collectInfoList){
            collectInfoVOList.add(collectInfo2CollectInfoVO(collectInfo));
        }
        return collectInfoVOList ;
    }

    @Override
    public CollectInfoVO getCollection(String collectionName) throws ApiException {
        CollectInfo collectInfo=collectRepository.findByCollectName(collectionName);
        if(collectInfo==null) throw new ApiException(ErrorEnum.COLLECT_IS_NOT_EXIST);
        return collectInfo2CollectInfoVO(collectInfo);
    }

    @Override
    public CollectInfoVO getCollectionById(String id) throws ApiException {
        CollectInfo collectInfo=collectRepository.findById(id).orElse(null);
        if(collectInfo==null) throw new ApiException(ErrorEnum.COLLECT_IS_NOT_EXIST);
        return collectInfo2CollectInfoVO(collectInfo);
    }

    @Transactional
    @Override
    public CollectInfoVO updateCollection(CollectForm collectForm, String oldName) throws ApiException {
        CollectInfo collectInfo=collectRepository.findByCollectName(oldName);
        if(collectInfo==null) throw  new ApiException(ErrorEnum.COLLECT_IS_NOT_EXIST);
        CollectInfo newCollectInfo=Converter.collectForm2CollectInfo(collectForm,collectInfo.getCollectId());
        newCollectInfo=collectRepository.save(newCollectInfo);
        return collectInfo2CollectInfoVO(newCollectInfo);
    }

    @Transactional
    @Override
    public boolean deleteCollections(String names) throws ApiException {
        List<String> nameList= Arrays.asList(names.split(";"));
        for (String name:nameList){
            getCollection(name);
        }
        collectRepository.deleteByCollectNameIn(nameList);
        return true;
    }

    @Override
    public boolean exists(String collectId) {
        if(collectRepository.findById(collectId).orElse(null)==null){
            return false;
        }
        return true;
    }

    @Override
    public CollectInfoVO collectInfo2CollectInfoVO(CollectInfo collectInfo) {
        String locationName;
        if(collectInfo.getLocationId()==null||"null".equals(collectInfo.getLocationId())||"".equals(collectInfo.getLocationId())) locationName=null;
        else locationName= locationRepository.findById(collectInfo.getLocationId()).orElse(null).getLocationName();
        String address;
        if(collectInfo.getLocationId()==null||"null".equals(collectInfo.getLocationId())||"".equals(collectInfo.getLocationId())) address=null;
        else address= locationRepository.findById(collectInfo.getLocationId()).orElse(null).getAddress();
        String departmentName;
        if(collectInfo.getDepartmentId()==null||"null".equals(collectInfo.getDepartmentId())||"".equals(collectInfo.getDepartmentId())) departmentName=null;
        else departmentName=departmentRepository.findById(collectInfo.getDepartmentId()).orElse(null).getName();
        String work;
        if(collectInfo.getWorkId()==null||"null".equals(collectInfo.getWorkId())||"".equals(collectInfo.getWorkId())){
            work=null;
        }else {
            WorkInfo workInfo=userRepository.findById(collectInfo.getWorkId()).orElse(null);
            work=workInfo.getName()+"("+workInfo.getWorkId()+")";
        }
        CollectInfoVO collectInfoVO=Converter.collectInfo2collectInfoVO(collectInfo,work,departmentName,locationName,address);
        return collectInfoVO;
    }

    @Override
    public List<CollectInfo> getAllCollectInfo() {
        return collectRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
         collectRepository.deleteById(id);
    }
}
