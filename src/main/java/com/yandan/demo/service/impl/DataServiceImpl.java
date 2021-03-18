package com.yandan.demo.service.impl;

import com.yandan.demo.dataobject.*;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.CollectRepository;
import com.yandan.demo.service.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create by yandan
 * 2020/11/21  21:57
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private DaySignService daySignService;
    @Autowired
    private DaySignTimesDepartmentService daySignTimesDepartmentService;
    @Autowired
    private DaySignTimesWorkService daySignTimesWorkService;
    @Autowired
    private SignTimesDepartmentService signTimesDepartmentService;
    @Autowired
    private SignTimesWorkService signTimesWorkService;


    public Thread taskThead;
    public Thread signTimesThread;
    private List<CollectInfo> collectInfoList=new ArrayList<CollectInfo>();
    private List<DepartmentInfo> departmentInfos=new ArrayList<DepartmentInfo>();
    private List<WorkInfo> workInfos=new ArrayList<WorkInfo>();
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String date="";
    private List<CollectInfo> outDateCollectInfos=new ArrayList<>();
    private     SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void taskService() {
            taskThead=new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                  while (true){
                      List<CollectInfo> collectInfos=collectionService.getAllCollectInfo();
                      for (CollectInfo collectInfo:collectInfos){
                          if((!hasCollectInfo(collectInfoList,collectInfo))&&(!hasCollectInfo(outDateCollectInfos,collectInfo))){
                              try {
                                  if(classifyCollectInfo(collectInfo)){
                                      List<WorkInfo> workInfos=userService.getAll();
                                      List<DepartmentInfo> departmentInfos=departmentService.getDepartments();
                                      String date=simpleDateFormat.format(new Date()).split(" ")[0];
                                      daySignTimesWorkService.addDaySignTimesByWorkInfo(workInfos,date);
                                      daySignTimesDepartmentService.addByDepartmentInfo(departmentInfos,date);
                                      daySignService.addByWorkInfoAndCollect(workInfos,collectInfo,date);
                                  }
                              } catch (ApiException e) {
                                  log.error("【taskService】code={},message={}",e.getCode(),e.getMessage());
                                  e.printStackTrace();
                              }

                          }
                      }
                      String nowTime=simpleDateFormat.format(new Date()).split(" ")[1];
                      if("00:00".equals(nowTime)){
                          collectInfoList.clear();
                          outDateCollectInfos.clear();
                      }
                      try {
                          taskThead.sleep(1000*30);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
                }
            });
            taskThead.start();

    }

    @Override
    public void countsService()  {
//        countsThread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//               while (true){
//                   String nowDate=simpleDateFormat.format(new Date()).split(" ")[0];
//                   String nowTime=simpleDateFormat.format(new Date()).split(" ")[1];
//                   try {
//                       countsThread.sleep(1000*60);
//                       if (nowTime.trim().equals("22:30")){
//                           List<DaySignWork> daySignWorkList = daySignService.getByDate(date);
//                           List<DepartmentInfo> departmentInfoList = departmentService.getDepartments();
//                           daySignTimesWorkService.updateDaySignTimesByDaySignWork(daySignWorkList);
//                           for (DepartmentInfo departmentInfo : departmentInfoList) {
//                               List<DaySignTimesWork> daySignTimesWorkList = daySignTimesWorkService.getByDepartmentIdAndDate(departmentInfo.getDepartmentId(), date);
//                               daySignTimesDepartmentService.updateByDaySignTimesWork(daySignTimesWorkList, date);
//                           }
//                           List<DaySignTimesWork> daySignTimesWorkList = daySignTimesWorkService.getDaySignTimesByDate(date);
//                           signTimesWorkService.updateByDaySignTimesWork(daySignTimesWorkList);
//                           for (DepartmentInfo departmentInfo : departmentInfoList) {
//                               List<SignTimesWork> signTimesWorkList = signTimesWorkService.getByDepartmentId(departmentInfo.getDepartmentId());
//                               signTimesDepartmentService.updateBySignTimesWork(signTimesWorkList);
//                           }
//                       }
//                       setTime(nowTime);
//                       if(!date.equals(nowDate)){
//                           updateCollectList();
//                           setDate(nowDate);
//                       }
//                   } catch (ApiException e) {
//                       log.error("【countsService】code={},message={}",e.getCode(),e.getMessage());
//                   } catch (InterruptedException e) {
//                       log.error("【InterruptedException】message={}",e.getMessage());
//                   }
//
//               }
//            }
//        });
//        countsThread.start();
    }
    public boolean hasCollectInfo(List<CollectInfo> list,CollectInfo collectInfo){
        for (CollectInfo collectInfo1:list){
            if (collectInfo1.getCollectId().equals(collectInfo.getCollectId())) return true;
        }
        return false;
    }
    public void setDate(String now){
        this.date=now;
    }
    public String getDate(){
        return this.date;
    }
    public void signTimesService() {
       signTimesThread=new Thread(new Runnable() {
           @Override
           public void run() {
               while (true){
                   try {
                       List<DepartmentInfo> departmentInfoList=departmentService.getDepartments();
                       for (DepartmentInfo departmentInfo:departmentInfoList){
                           if(!hasDepartmentInfo(departmentInfo)){
                               signTimesDepartmentService.addByDepartmentInfo(departmentInfo);
                               addDepartmentInfo(departmentInfo);
                           }
                       }
                       List<WorkInfo> workInfoList=userService.getAll();
                       for (WorkInfo workInfo:workInfoList){
                           if(!hasWorkInfo(workInfo)){
                               signTimesWorkService.addByWorkInfo(workInfo);
                               addWorkInfo(workInfo);
                           }
                       }
                       signTimesThread.sleep(1000*10);
                   } catch (ApiException e) {
                       log.error("【signTimesService】code={},message={}",e.getCode(),e.getMessage());
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       log.error("【InterruptedException】message={}",e.getMessage());
                   }
               }
           }
       });
       signTimesThread.start();
    }
    public boolean classifyCollectInfo(CollectInfo collectInfo) throws ParseException {
        //true 没过期  false 已过期
            CollectInfo collectInfo1=new CollectInfo();
            long end=simpleDateFormat.parse(collectInfo.getEndDate()+" "+collectInfo.getEndTime()).getTime();
            long now=new Date().getTime();
            long nowDate=sm.parse(sm.format(new Date())).getTime();
            long startDate=sm.parse(collectInfo.getStartDate()).getTime();
            if(now>=end){
                BeanUtils.copyProperties(collectInfo,collectInfo1);
                outDateCollectInfos.add(collectInfo1);
                return false;
            }else if(nowDate>startDate&&collectInfo.getDayTimes()==0){
                BeanUtils.copyProperties(collectInfo,collectInfo1);
                outDateCollectInfos.add(collectInfo1);
                return false;
            }else{
                BeanUtils.copyProperties(collectInfo,collectInfo1);
                this.collectInfoList.add(collectInfo1);
                return true;
            }

    }

    public boolean hasDepartmentInfo(DepartmentInfo departmentInfo){
        for (DepartmentInfo departmentInfo1:this.departmentInfos){
            if(departmentInfo.getDepartmentId().equals(departmentInfo1.getDepartmentId())) return true;
        }
        return false;
    }
    public boolean hasWorkInfo(WorkInfo workInfo){
        for (WorkInfo workInfo1 :this.workInfos){
            if(workInfo.getWorkId().equals(workInfo1.getWorkId())) return true;
        }
        return false;
    }
    public void addDepartmentInfo(DepartmentInfo departmentInfo){
        this.departmentInfos.add(departmentInfo);
    }
    public void addWorkInfo(WorkInfo workInfo){
        this.workInfos.add(workInfo);
    }



}
