package uz.tafakkur.sport.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.tafakkur.sport.domain.enumeration.ProfileStatus;
import uz.tafakkur.sport.service.dto.ProfileDTO;

@Service
@Transactional
public class ApiService {
    private final Logger log = LoggerFactory.getLogger(ApiService.class);

    private final ProfileService profileService;

    public ApiService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public ProfileDTO createOrUpdateProfile(Update update) {
        ProfileDTO profileDTO = profileService.getProfileByChatId(update.getMessage().getChatId());
        profileDTO.setChatId(update.getMessage().getChatId());
        profileDTO.setFullName(getFullName(update));
        profileDTO.setUserName(update.getMessage().getFrom().getUserName());
        profileDTO.setStatus(ProfileStatus.ACTIVE);
        return profileService.save(profileDTO);
    }

    public String getFullName(Update update) {
        String firstName = "";
        String lastName = "";

        if (update.getMessage() != null) {
            if (update.getMessage().getFrom().getFirstName() != null) {
                firstName = update.getMessage().getFrom().getFirstName();
            }
            if (update.getMessage().getFrom().getLastName() != null) {
                lastName = update.getMessage().getFrom().getLastName();
            }
        } else if (update.getCallbackQuery() != null) {
            if (update.getCallbackQuery().getFrom().getFirstName() != null) {
                firstName = update.getCallbackQuery().getFrom().getFirstName();
            }
            if (update.getCallbackQuery().getFrom().getLastName() != null) {
                lastName = update.getCallbackQuery().getFrom().getLastName();
            }
        }
        return firstName + (lastName.length() > 0 ? (" " + lastName) : "");
    }
}
