package com.oozeander.courses_manager_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oozeander.courses_manager_api.RacesManagerApiApplication;
import com.oozeander.courses_manager_api.common.IntegrationTests;
import com.oozeander.courses_manager_api.dto.ErrorResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RacesManagerApiApplication.class)
@AutoConfigureMockMvc
class RaceManagerControllerTest extends IntegrationTests {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Nested
    class AddRace {

        private static final String URI_SUFFIX = "/race-manager";

        @Test
        void should_save_and_publish_race() throws Exception {
            mvc.perform(MockMvcRequestBuilders.post(URI_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "date": "2023-01-29",
                                        "number": 1,
                                        "name": "Prix d'Amérique",
                                        "participants": [
                                            {
                                                "number": 1,
                                                "name": "HORSY DREAM"
                                            },
                                            {
                                                "number": 2,
                                                "name": "HOOKER BERRY"
                                            },
                                            {
                                                "number": 3,
                                                "name": "FLAMME DU GOUTIER"
                                            },
                                            {
                                                "number": 4,
                                                "name": "IDAO DE TILLARD"
                                            }
                                        ]
                                    }""")
                    )
                    .andExpect(status().isOk());
        }

        @Test
        void should_not_save_nor_publish_if_there_is_a_validation_error() throws Exception {
            MvcResult result = mvc.perform(MockMvcRequestBuilders.post(URI_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "date": "2023-01-29",
                                        "participants": [
                                            {
                                                "number": 1,
                                                "name": "HORSY DREAM"
                                            },
                                            {
                                                "number": 2,
                                                "name": "HOOKER BERRY"
                                            }
                                        ]
                                    }""")
                    )
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
            assertAll(
                    () -> assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.errors()).containsExactlyInAnyOrder(
                            "race.participants[].size must be at least 3", "race.number must be provided", "race.name must be provided")
            );
        }

        @Test
        void should_not_save_nor_publish_if_there_is_not_a_linear_relationship_between_each_participant_numbers() throws Exception {
            MvcResult result = mvc.perform(MockMvcRequestBuilders.post(URI_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "date": "2023-01-29",
                                        "number": 1,
                                        "name": "Prix d'Amérique",
                                        "participants": [
                                            {
                                                "number": 1,
                                                "name": "HORSY DREAM"
                                            },
                                            {
                                                "number": 2,
                                                "name": "HOOKER BERRY"
                                            },
                                            {
                                                "number": 4,
                                                "name": "IDAO DE TILLARD"
                                            }
                                        ]
                                    }""")
                    )
                    .andExpect(status().isBadRequest())
                    .andReturn();

            ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
            assertAll(
                    () -> assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.errors()).containsExactly("participants[].number must begin at 1, be distinct as well as have no holes in between")
            );
        }
    }
}