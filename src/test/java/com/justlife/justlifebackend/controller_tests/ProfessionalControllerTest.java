package com.justlife.justlifebackend.controller_tests;

import com.justlife.justlifebackend.controller.ProfessionalController;
import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.repository.ProfessionalRepository;
import com.justlife.justlifebackend.service.impl.AppointmentService;
import com.justlife.justlifebackend.service.impl.ProfessionalService;
import com.justlife.justlifebackend.service.impl.VehicleService;
import com.justlife.justlifebackend.specification.AppointmentSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProfessionalControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    ProfessionalController professionalController;

    @Mock
    ProfessionalService professionalService;



    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(professionalController)
                .build();
    }

    @Test
    public void shouldGetAvailableProfessionalsForGivenDate() throws Exception {

        String date = "2021-10-03T11:59:11.332";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/professional/available-professionals-for-date")
                                                    .param("date",date)).andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void shouldGetAvailableProfessionalsForGivenDateAndDuration() throws Exception {

        String date = "2021-10-03T11:30:00.000";
        String duration = "4";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/professional/available-professionals-for-date-and-duration")
                .param("startTime",date)
                .param("duration",duration)).andExpect(MockMvcResultMatchers.status().isOk());

    }

}
