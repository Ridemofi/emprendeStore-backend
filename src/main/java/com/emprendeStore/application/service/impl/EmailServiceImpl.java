package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void enviarCorreoBienvenida(String destinatario, String nombreUsuario) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Configuración del remitente
            helper.setFrom("onboarding@resend.dev", "EmprendeStore");

            helper.setTo(destinatario);
            helper.setSubject("🎁 ¡Bienvenido a EmprendeStore! Grandes rebajas te esperan");

            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <body style="margin: 0; padding: 0; background-color: #f4f4f4; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; margin-top: 20px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
                        
                        <div style="background-color: #ff5722; padding: 30px 20px; text-align: center;">
                            <h1 style="color: #ffffff; margin: 0; font-size: 28px; font-weight: 800; letter-spacing: 1px;">EmprendeStore</h1>
                            <p style="color: #ffe0b2; margin: 5px 0 0 0; font-size: 14px;">Tu mundo de ofertas increíbles</p>
                        </div>

                        <div style="padding: 40px 30px; text-align: center;">
                            <h2 style="color: #333333; font-size: 24px; margin-bottom: 20px;">¡Hola, %s! 👋</h2>
                            
                            <p style="color: #666666; font-size: 16px; line-height: 1.6; margin-bottom: 25px;">
                                ¡Ya eres parte de la familia! Prepárate para descubrir millones de productos a precios que no podrás creer. 
                                Desde tecnología hasta moda, tenemos todo lo que buscas.
                            </p>

                            <div style="background-color: #fff3e0; border-radius: 8px; padding: 15px; margin-bottom: 30px; display: inline-block; width: 100%%; box-sizing: border-box;">
                                <table width="100%%" style="border-collapse: collapse;">
                                    <tr>
                                        <td align="center" style="padding: 5px;"><span style="font-size: 24px;">🚀</span><br><span style="font-size: 12px; color: #d84315; font-weight: bold;">Envíos Rápidos</span></td>
                                        <td align="center" style="padding: 5px;"><span style="font-size: 24px;">🛡️</span><br><span style="font-size: 12px; color: #d84315; font-weight: bold;">Compra Segura</span></td>
                                        <td align="center" style="padding: 5px;"><span style="font-size: 24px;">🏷️</span><br><span style="font-size: 12px; color: #d84315; font-weight: bold;">Mejores Precios</span></td>
                                    </tr>
                                </table>
                            </div>

                            <a href="https://tutienda.com/home" style="background-color: #ff5722; color: #ffffff; padding: 15px 40px; text-decoration: none; border-radius: 50px; font-weight: bold; font-size: 18px; display: inline-block; box-shadow: 0 4px 6px rgba(255, 87, 34, 0.3);">
                                EXPLORAR AHORA
                            </a>
                            
                            <p style="margin-top: 30px; font-size: 14px; color: #999;">
                                ¿Buscas algo específico? <a href="#" style="color: #ff5722; text-decoration: none;">Ver categorías populares</a>
                            </p>
                        </div>

                        <div style="background-color: #f8f9fa; padding: 20px; text-align: center; border-top: 1px solid #eeeeee;">
                            <p style="color: #b0b0b0; font-size: 12px; margin: 0;">
                                © 2024 EmprendeStore Inc. Todos los derechos reservados.
                            </p>
                        </div>
                    </div>
                    <div style="height: 40px;"></div>
                </body>
                </html>
                """.formatted(nombreUsuario);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo de bienvenida: " + e.getMessage());
        }
    }
}