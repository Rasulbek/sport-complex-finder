package uz.tafakkur.sport.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.tafakkur.sport.service.ApiService;
import uz.tafakkur.sport.service.dto.ProfileDTO;

@Service
public class BotService {
    private final Logger log = LoggerFactory.getLogger(BotService.class);

    private final ApiService apiService;

    public BotService(ApiService apiService) {
        this.apiService = apiService;
    }

    public ProfileDTO createOrUpdateProfile(Update update) {
        return apiService.createOrUpdateProfile(update);
    }
}
