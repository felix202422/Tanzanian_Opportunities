import i18n from "i18next";
import { initReactI18next } from "react-i18next";

void i18n.use(initReactI18next).init({
  resources: {
    en: {
      translation: {
        foundation: {
          eyebrow: "Development foundation",
          title: "Tanzania Digital Opportunity Platform",
          description:
            "The application workspace is initialized. Public product features will be delivered in their approved phases.",
          status: "Phase 1 foundation is in progress.",
        },
      },
    },
    sw: {
      translation: {
        foundation: {
          eyebrow: "Msingi wa uendelezaji",
          title: "Jukwaa la Fursa za Kidijitali Tanzania",
          description:
            "Mazingira ya programu yameanzishwa. Vipengele vya bidhaa vitatolewa katika awamu zilizoidhinishwa.",
          status: "Msingi wa Awamu ya 1 unaendelea.",
        },
      },
    },
  },
  lng: "en",
  fallbackLng: "en",
  interpolation: {
    escapeValue: false,
  },
});

export default i18n;
