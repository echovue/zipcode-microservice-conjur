package com.echovue.controller;

import com.echovue.service.ZipcodeDistanceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ZipcodeControllerTest {

    @Mock
    private ZipcodeDistanceService zipcodeDistanceService;

    @InjectMocks
    @Resource
    ZipcodeController zipcodeController = new ZipcodeController(zipcodeDistanceService);

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDistance() throws Exception {
        when(zipcodeDistanceService.getDistance(anyString(), anyString()))
                .thenReturn(Optional.of(new Double("533")));
        String result = zipcodeController.getDistance("97035", "94016");
        assertEquals("533 miles", result);
    }


    @Test
    public void getDistanceRoundUp() throws Exception {
        when(zipcodeDistanceService.getDistance(anyString(), anyString()))
                .thenReturn(Optional.of(new Double("532.94")));
        String result = zipcodeController.getDistance("97035", "94016");
        assertEquals("533 miles", result);
    }


    @Test
    public void getDistanceRoundDown() throws Exception {
        when(zipcodeDistanceService.getDistance(anyString(), anyString()))
                .thenReturn(Optional.of(new Double("533.14")));
        String result = zipcodeController.getDistance("97035", "94016");
        assertEquals("533 miles", result);
    }

    @Test
    public void getDistanceVoid() throws Exception {
        when(zipcodeDistanceService.getDistance(anyString(), anyString()))
                .thenReturn(Optional.empty());
        String result = zipcodeController.getDistance("97035", "94016");
        assertEquals("Unable to calculate distance", result);
    }



}