package com.vadpol.ex.service.impl;

import com.vadpol.ex.entity.Notification;
import com.vadpol.ex.repository.NotificationRepository;
import com.vadpol.ex.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(Notification notification) {
        notificationRepository.save(notification);

    }
}
