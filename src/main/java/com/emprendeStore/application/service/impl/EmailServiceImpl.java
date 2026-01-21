package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.mail.password}")
    private String resendApiKey;

    @Value("${spring.mail.from}")
    private String fromEmail;

    @Async
    @Override
    public void enviarCorreoBienvenida(String destinatario, String nombreUsuario) {
        try {
            String url = "https://api.resend.com/emails";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + resendApiKey);

            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <body style="margin: 0; padding: 0; background-color: #f4f4f4; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; margin-top: 20px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
                        
                        <div style="background-color: #ff5722; padding: 30px 20px; text-align: center;">
                            <h1 style="color: #ffffff; margin: 0; font-size: 28px; font-weight: 800; letter-spacing: 1px;">EmprendeStore</h1>
                        </div>

                        <div style="padding: 40px 30px; text-align: center;">
                            <h2 style="color: #333333; font-size: 24px; margin-bottom: 20px;">¡Hola, %s! 👋</h2>
                            
                            <p style="color: #666666; font-size: 16px; line-height: 1.6; margin-bottom: 25px;">
                                ¡Ya eres parte de la familia! Prepárate para descubrir millones de productos a precios increíbles. 
                                Desde tecnología hasta moda, tenemos todo lo que buscas.
                            </p>

                            <div style="background-color: #fff3e0; border-radius: 8px; padding: 15px; margin-bottom: 30px; display: inline-block; width: 100%%; box-sizing: border-box;">
                                <p style="color: #d84315; font-weight: bold; margin: 0;">
                                    🚀 Envíos Rápidos &nbsp;|&nbsp; 🛡️ Compra Segura &nbsp;|&nbsp; 🏷️ Mejores Precios
                                </p>
                            </div>
                        </div>

                        <div style="background-color: #f8f9fa; padding: 20px; text-align: center; border-top: 1px solid #eeeeee;">
                            <p style="color: #b0b0b0; font-size: 12px; margin: 0;">
                                © 2024 EmprendeStore Inc.
                            </p>
                        </div>
                    </div>
                    <div style="height: 40px;"></div>
                </body>
                </html>
                """.formatted(nombreUsuario);

            Map<String, Object> body = new HashMap<>();
            body.put("from", "EmprendeStore <" + fromEmail + ">");
            body.put("to", destinatario);
            body.put("subject", "🎁 ¡Bienvenido a EmprendeStore!");
            body.put("html", htmlContent);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(url, request, String.class);

            System.out.println("✅ Correo enviado vía HTTP API a: " + destinatario);

        } catch (Exception e) {
            System.err.println("❌ Error crítico enviando correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}