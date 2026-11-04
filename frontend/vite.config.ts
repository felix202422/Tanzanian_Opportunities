import tailwindcss from "@tailwindcss/vite";
import react from "@vitejs/plugin-react";
import { defineConfig, loadEnv } from "vite";

export default defineConfig(({ mode }) => {
  const environment = loadEnv(mode, "..", "");
  const apiTarget =
    environment.TDOP_API_PROXY_TARGET ??
    `http://127.0.0.1:${environment.SERVER_PORT ?? "8080"}`;

  return {
    envDir: "..",
    plugins: [react(), tailwindcss()],
    server: {
      host: "127.0.0.1",
      port: Number(environment.VITE_DEV_PORT ?? "5173"),
      strictPort: true,
      proxy: {
        "/api": {
          target: apiTarget,
          changeOrigin: true,
        },
      },
    },
  };
});
