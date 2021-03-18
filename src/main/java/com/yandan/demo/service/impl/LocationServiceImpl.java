package com.yandan.demo.service.impl;

import com.yandan.demo.config.MyConfig;
import com.yandan.demo.dataobject.Location;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.LocationRepository;
import com.yandan.demo.service.LocationService;
import com.yandan.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/16  13:50
 */
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private MyConfig myConfig;
    @Override
    public List<Location> getLocationList() {
        return locationRepository.findAll();
    }

    @Transactional
    @Override
    public Location addLocation(Location location) throws ApiException {
        if(exists(location.getLocationName())) throw new ApiException(ErrorEnum.LOCATION_ALREADY_EXIST);
        if (location.getLocationInfo()==null||"".equals(location.getLocationInfo()))  throw new ApiException(ErrorEnum.LOCATION_INFO_IS_EMPTY);
        int n=myConfig.getLocationNumber();
        String key= KeyUtil.getKey(n);
        while (exists(key)) key=KeyUtil.getKey(n);
         location.setLocationId(key);
        return locationRepository.save(location);
    }

    @Transactional
    @Override
    public Location updateLocation(Location newLocation) throws ApiException {
        if(!exists(newLocation.getLocationId())) throw new ApiException(ErrorEnum.LOCATION_IS_NOT_EXIST);
        if(exists(newLocation.getLocationName())) throw new ApiException(ErrorEnum.LOCATION_ALREADY_EXIST);
        return locationRepository.save(newLocation);
    }

    @Override
    public Location getLocationByName(String locationName) throws ApiException {
        Location location=locationRepository.findByLocationName(locationName);
        if(location==null) throw new ApiException(ErrorEnum.LOCATION_IS_NOT_EXIST);
        return location ;
    }

    @Override
    public Location getLocationById(String locationId) throws ApiException {
        Location location=locationRepository.findById(locationId).orElse(null);
        if (location==null) throw new ApiException(ErrorEnum.LOCATION_IS_NOT_EXIST);
        return location;
    }

    @Transactional
    @Override
    public boolean deleteLocationsByName(String names) throws ApiException {
        List<String> nameList= Arrays.asList(names.split(";"));
        for(String name:nameList){
          getLocationByName(name);
        }
        locationRepository.deleteByLocationNameIn(nameList);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteLocationsById(String ids) throws ApiException {
        List<String> idList=Arrays.asList(ids.split(";"));
        for (String id:idList){
            getLocationById(id);
        }
        locationRepository.deleteByLocationIdIn(idList);
        return true;
    }

    @Override
    public boolean exists(String nameOrId) {
        Location location=locationRepository.findById(nameOrId).orElse(null);
        if(location!=null) return true;
        location=locationRepository.findByLocationName(nameOrId);
        if (location!=null) return true;
        return false;
    }
}
