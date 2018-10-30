package com.lnu.foundation.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/connect")
public class ChangeDefaultFlowController extends ConnectController {

    public ChangeDefaultFlowController(ConnectionFactoryLocator connectionFactoryLocator,
                                       ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected String connectedView(String providerId) {
        return "redirect:/" + providerId;
    }

    @Override
    protected RedirectView connectionStatusRedirect(String providerId, NativeWebRequest request) {
        return new RedirectView("/" + providerId, true);
    }

}
