# Guía de Contribución — PS-P2 SecureBank API

## Regla fundamental

> **TODOS los integrantes del grupo DEBEN abrir su propio Pull Request cada semana.**
> Si no hay PR tuyo → no tienes entrega → no tienes nota esa semana.
> El líder no puede subir el trabajo de todos. Cada uno sube el suyo.

---

## Estructura de tu entrega semanal

```
semana-XX/
└── TU_APELLIDO_NOMBRE/
    ├── avance_sXX.md        ← Descripción de tu aporte específico
    ├── codigo/              ← Tu código (si te tocó desarrollar)
    └── evidencia/           ← Capturas / tests / reportes
```

---

## Flujo para hacer tu PR semanal

```bash
# 1. Clona el repo (primera vez)
git clone https://github.com/TU_USUARIO/PS-P2-SecureBank-API.git
cd PS-P2-SecureBank-API

# 2. Agrega el upstream del líder (primera vez)
git remote add upstream https://github.com/[LIDER]/PS-P2-SecureBank-API.git

# 3. Actualiza antes de trabajar
git fetch upstream && git merge upstream/main && git push origin main

# 4. Trabaja en tu carpeta
mkdir -p semana-03/APELLIDO_NOMBRE

# 5. Commit y push
git add semana-03/APELLIDO_NOMBRE/
git commit -m "feat(s03): [tu aporte] - APELLIDO NOMBRE"
git push origin main

# 6. Abre PR en GitHub hacia el repo del grupo
```

---

## Lo que NO se permite

- Subir credenciales, API keys bancarias o tokens reales
- Subir archivos `.env` con datos reales
- Subir claves privadas o certificados de producción
- Hacer push directo a `main`
- Enviar el PR por otro compañero

---

## Convención de commits

```
feat(s02): implementa validación de IBAN - GARCIA JUAN
fix(s03): previene desbordamiento en monto de transferencia - LOPEZ MARIA
test(s04): agrega tests de autorización por scope - QUISPE CARLOS
```

---

*DD281 Programación Segura — Universidad Autónoma del Perú — 2026-1*
