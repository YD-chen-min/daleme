package com.yandan.demo.service;

import com.yandan.demo.dataobject.Border;
import com.yandan.demo.dataobject.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yandan
 * 2020/12/22  9:40
 */
@Service
public class InService {
    private Border border;

    public void setBorder(List<String> strs){
        this.border=new Border();
        Point[] points=new Point[2];
        points[0]=new Point();
        points[1]=new Point();
        points[0].setLng(Double.parseDouble(strs.get(0).split(",")[0]));
        points[0].setLat(Double.parseDouble(strs.get(0).split(",")[1]));
        points[1].setLng(Double.parseDouble(strs.get(1).split(",")[0]));
        points[1].setLat(Double.parseDouble(strs.get(1).split(",")[1]));
        border.setCenter(points[0]);
        double x=points[1].getLng()-points[0].getLng();
        double y=points[1].getLat()-points[0].getLat();
        double r2= Math.pow(x,2)+Math.pow(y,2);
        double r= Math.sqrt(r2);
        border.setR(r);
    }
    public boolean in(Point point){
        double x=point.getLng()-this.border.getCenter().getLng();
        double y=point.getLat()-this.border.getCenter().getLat();
        double r2= (double) (Math.pow(x,2)+Math.pow(y,2));
        double r= (double) Math.sqrt(r2);
        if (r<=this.border.getR()){
            return true;
        }
        return false;
    }
}
