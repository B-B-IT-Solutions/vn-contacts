package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.NetworkingUtils.referralSuggestions;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.NetworkingAssertions.assertPage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.contacts.NetworkingMapper;
import cz.prm.services.contacts.networking.NetworkingClearingHouse;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NetworkingControllerTest {

    @Mock
    private NetworkingClearingHouse clearingHouse;

    private NetworkingMapper mapper = MapperUtils.getNetworkingMapper();
    private NetworkingController controller;

    @BeforeEach
    void setUp() {
        controller = new NetworkingController(clearingHouse, mapper);
    }

    @Test
    void getReferralSuggestions() {
        var referralSuggestions = referralSuggestions();
        var page = page(referralSuggestions);
        var contactId = randomLong();
        when(clearingHouse.getReferralSuggestions(contactId)).thenReturn(page);

        var responseDto = controller.getReferralSuggestions(contactId);
        verify(clearingHouse).getReferralSuggestions(contactId);
        assertPage(page, responseDto);
    }
}