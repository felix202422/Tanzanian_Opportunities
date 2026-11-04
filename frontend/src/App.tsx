import { useTranslation } from "react-i18next";

export default function App() {
  const { t } = useTranslation();

  return (
    <main className="mx-auto flex min-h-screen max-w-5xl items-center px-6 py-16">
      <section aria-labelledby="page-title" className="max-w-2xl">
        <p className="text-sm font-semibold uppercase tracking-[0.18em] text-blue-700">
          {t("foundation.eyebrow")}
        </p>
        <h1
          className="mt-4 text-4xl font-semibold tracking-tight text-slate-950 sm:text-5xl"
          id="page-title"
        >
          {t("foundation.title")}
        </h1>
        <p className="mt-6 text-lg leading-8 text-slate-600">
          {t("foundation.description")}
        </p>
        <p
          aria-live="polite"
          className="mt-8 text-sm font-medium text-slate-700"
        >
          {t("foundation.status")}
        </p>
      </section>
    </main>
  );
}
