# PS-P2 — UQ·FinSecure | Secure Banking API with AI Fraud Detection
### DD281 Programación Segura · Universidad Autónoma del Perú · 2026-1 · UQ AI SOLUTION COMPANY SAC

[![Grupo](https://img.shields.io/badge/Grupo-G2-blue?style=for-the-badge)]()
[![Stack](https://img.shields.io/badge/Stack-Python%20%7C%20FastAPI%20%7C%20Azure-orange?style=for-the-badge)]()
[![Journal](https://img.shields.io/badge/Target-J.InfoSec_%26_Applications_Q1-red?style=for-the-badge)]()
[![Demo](https://img.shields.io/badge/Demo-finsecure.uqaisolutions.com.pe-green?style=for-the-badge)]()

---

## 🏆 Los 5 Títulos Scopus Q1 del Curso DD281-2026-1

> Todos los grupos del curso publican su investigación en journals Scopus Q1 al término del ciclo.
> El docente es coautor y guía el proceso de publicación.

| Grupo | Proyecto | Título Scopus Q1 Optimizado | Journal Target | Q |
|:---:|---|---|---|:---:|
| G1 | UQ·SecureID | "Zero-Trust Behavioral Authentication: A Machine Learning-Enhanced Identity Verification Framework with Anomaly Detection for Continuous User Authentication in Cloud-Native Web Applications" | Computers & Security — Elsevier | Q1 |
| **G2** | **UQ·FinSecure** | **"SecureFinAPI: A Hybrid Machine Learning and Rule-Based Fraud Detection System for RESTful Banking APIs Compliant with OWASP API Security Top 10"** | **J. Information Security & Applications — Elsevier** | **Q1** |
| G3 | UQ·HealthShield | "PrivacyShield: An End-to-End Encrypted Electronic Health Record System with Attribute-Based Access Control for HIPAA and Ley N°29733 Compliance" | J. Biomedical Informatics — Elsevier | Q1 |
| G4 | UQ·CivicVote | "CryptoVote: A Blockchain-Enhanced Electronic Voting Protocol with Zero-Knowledge Proof Verification for Tamper-Resistant and Privacy-Preserving Democratic Processes" | Future Generation Computer Systems — Elsevier | Q1 |
| G5 | UQ·AuditAI | "AutoPenTest-AI: An Artificial Intelligence-Driven Automated Web Penetration Testing Framework with Natural Language Vulnerability Reporting Based on OWASP Top 10" | IEEE Access — IEEE | Q1 |

---

## 📄 Tu Proyecto: G2 — UQ·FinSecure

### Título Scopus Q1 Optimizado

> **"SecureFinAPI: A Hybrid Machine Learning and Rule-Based Fraud Detection System for RESTful Banking APIs Compliant with OWASP API Security Top 10"**
>
> 🎯 **Journal:** Journal of Information Security and Applications — Elsevier — **Q1** — Impact Factor: 4.9
> 🔗 **Verificar cuartil:** https://www.scimagojr.com/journalsearch.php?q=Journal+of+Information+Security+and+Applications

---

### ❗ Problema que Resuelve

Las APIs financieras son el **objetivo #1 de ataques** en el sector fintech. Según el OWASP API Security Report 2023:
- El **34% de las APIs bancarias** tienen broken object level authorization (BOLA/IDOR)
- Las pérdidas por fraude digital en Latam superaron los **$9,900 millones USD** en 2023
- El **91% de los fraudes** se detectan tarde (después de la transacción, no en tiempo real)

Los problemas técnicos concretos que resuelve UQ·FinSecure:
1. **Sin detección en tiempo real**: los sistemas tradicionales detectan fraude post-mortem
2. **APIs sin autorización granular**: un usuario puede acceder a cuentas de otros (IDOR)
3. **Sin límites de velocidad**: un atacante puede intentar miles de transacciones sin ser bloqueado
4. **Logs insuficientes**: no hay trazabilidad completa para auditoría

**UQ·FinSecure** resuelve esto con:
- Modelo híbrido: 60% XGBoost ML + 40% reglas heurísticas para detección de fraude
- Autorización estricta: cada transacción verifica `account.user_id == current_user.id`
- Rate limiting por IP + usuario con Redis
- Audit trail completo e inmutable en cada operación

---

### 🎯 Objetivo del Proyecto

**Objetivo General:**
Desarrollar e implementar una API REST de nivel bancario con FastAPI que integra detección híbrida de fraude (ML + heurísticas), cumple el OWASP API Security Top 10 completo y sirve como backend financiero demostrable para UQ AI SOLUTION COMPANY SAC.

**Objetivos Específicos:**
1. Implementar autenticación JWT con refresh tokens y blacklist para revocación
2. Diseñar el motor de detección de fraude con XGBoost (AUC-ROC ≥ 0.92)
3. Aplicar los 10 controles del OWASP API Security Top 10 (API1–API10)
4. Construir el sistema de audit trail con firma de integridad por registro
5. Desplegar en Azure App Services bajo `finsecure.uqaisolutions.com.pe`

---

## 📅 Plan de Desarrollo por Semanas (8 Semanas)

### Visión general

```
S1 → Diseño + Setup FastAPI
S2 → Auth JWT + cuentas bancarias
S3 → Transacciones seguras (anti-IDOR, anti-tampering)
S4 ★ EP: Exposición 60%
S5 → Fraud Detection ML (XGBoost)
S6 → Azure Deploy + Redis rate limiting
S7 → OWASP API Top 10 audit completo
S8 ★ EF: Presentación Final 100%
```

---

### SEMANA 1 — Setup, Arquitectura y Modelo de Amenazas Financiero

**Objetivo:** Diseñar la arquitectura financiera y documentar el modelo de amenazas antes de codificar.

**Tareas del equipo:**
- [ ] Hacer fork del repositorio: `https://github.com/RubenCarty/PS-P2-SecureBank-API`
- [ ] Cada integrante clona su fork y configura `upstream`
- [ ] Configurar entorno Python 3.11 + FastAPI + Pydantic v2
- [ ] Diseñar el modelo de datos: User, Account, Transaction, AuditLog
- [ ] Realizar análisis de amenazas financiero (STRIDE + OWASP API Top 10 checklist)
- [ ] Documentar en `docs/semana-01/arquitectura_financiera.md`
- [ ] Definir los endpoints de la API con OpenAPI/Swagger (diseño first)

**Branch a crear:**
```bash
git checkout -b feature/S1-ApellidoNombre-setup-arquitectura
```

**PR hacia:** `main` del repo del docente
**Checklist PR S1:** arquitectura documentada + threat model + endpoints diseñados + FastAPI corriendo

---

### SEMANA 2 — Autenticación JWT + Gestión de Cuentas

**Objetivo:** Sistema de autenticación stateless con JWT y gestión básica de cuentas bancarias.

**Tareas del equipo:**
- [ ] Implementar registro/login con bcrypt (salt rounds ≥ 12)
- [ ] Generar JWT access token (15 min) + refresh token (7 días)
- [ ] Implementar blacklist de tokens revocados (Redis o BD)
- [ ] Endpoints CRUD de cuentas bancarias con validación Pydantic v2
- [ ] Aplicar `@require_auth` en todos los endpoints de cuenta
- [ ] Rate limiting: máx 5 intentos de login por IP/15min
- [ ] Documentación OpenAPI autogenerada con ejemplos seguros

**Branch a crear:**
```bash
git checkout -b feature/S2-ApellidoNombre-jwt-auth-cuentas
```

**Tests mínimos:** login exitoso retorna JWT válido, token expirado rechazado, refresh funciona

---

### SEMANA 3 — Transacciones Seguras (Anti-IDOR, Anti-Tampering, ACID)

**Objetivo:** Motor de transacciones que sea imposible de explotar via IDOR o manipulación.

**Tareas del equipo:**
- [ ] Implementar `POST /accounts/{id}/transfer`
- [ ] Verificar ownership: `account.user_id == current_user.id` (PREVENCIÓN IDOR)
- [ ] Verificar saldo antes de debitar (no asumir el cliente es honesto)
- [ ] Transacción ACID: debit + credit + registro en audit_log en una sola transacción
- [ ] Rollback automático si algún paso falla
- [ ] Validar monto con Pydantic: `amount: Decimal = Field(gt=0, le=100000)`
- [ ] Registrar IP, timestamp, estado en cada transacción

**Branch a crear:**
```bash
git checkout -b feature/S3-ApellidoNombre-transacciones-seguras
```

**Tests mínimos:** IDOR rechazado (cuenta de otro usuario), saldo insuficiente, transferencia exitosa completa

---

### SEMANA 4 ★ — EP: EVALUACIÓN PARCIAL (60% del proyecto)

**Objetivo:** Demo funcional con auth JWT + cuentas + transacciones seguras implementadas.

**Entregables OBLIGATORIOS:**
1. **Pull Request** en GitHub con todos los avances S1–S4 integrados en `main`
2. **Demo en vivo** (15 minutos):
   - Login → obtener JWT → usar token en Swagger UI
   - Crear 2 cuentas y hacer una transferencia
   - Intentar acceder a cuenta de otro usuario (debe retornar 403)
   - Intentar monto negativo o mayor al saldo (debe ser rechazado)
   - Mostrar audit log de las operaciones
3. **Tests corriendo** en GitHub Actions (badge verde)

**Branch a crear:**
```bash
git checkout -b release/EP-S4-NombreGrupo
```

**Rúbrica EP (100 puntos):**
| Criterio | Puntos |
|---|:---:|
| Auth JWT funcional con refresh + revocación | 30 |
| Transacciones con prevención IDOR + ACID | 25 |
| Validación Pydantic en todos los endpoints | 20 |
| Audit log completo | 10 |
| Tests automatizados (cobertura ≥ 60%) | 15 |

---

### SEMANA 5 — Fraud Detection con XGBoost

**Objetivo:** Modelo de ML que bloquea transacciones fraudulentas en tiempo real.

**Tareas del equipo:**
- [ ] Implementar `app/fraud/detector.py` con XGBoost
- [ ] Extraer 6 features: monto, hora, tx_count_24h, desviación_monto, es_internacional, ip_risk_score
- [ ] Scoring híbrido: 60% ML + 40% heurísticas (montos altos, 2-5 AM, muchas tx)
- [ ] Thresholds: low/medium/high/very_high/critical → bloquear si critical
- [ ] Fallback a heurísticas si el modelo no está entrenado (cold start)
- [ ] Dashboard `/admin/fraud` con estadísticas de detección
- [ ] Generar dataset sintético de entrenamiento con `tests/fixtures/generate_dataset.py`

**Branch a crear:**
```bash
git checkout -b feature/S5-ApellidoNombre-fraud-detection-ml
```

---

### SEMANA 6 — Azure Deploy + Redis Rate Limiting

**Objetivo:** API corriendo en `finsecure.uqaisolutions.com.pe` con infraestructura de producción.

**Tareas del equipo:**
- [ ] Crear Azure App Service (Python 3.11, westus3)
- [ ] Configurar Azure Cache for Redis (rate limiting)
- [ ] Configurar Azure SQL Database con conexión encriptada
- [ ] Migrar secrets a Azure Key Vault
- [ ] Completar CI/CD: tests → Bandit → Safety → deploy
- [ ] Configurar CORS explícito (lista blanca de orígenes)
- [ ] Verificar TrustedHostMiddleware activo en producción

**Branch a crear:**
```bash
git checkout -b feature/S6-ApellidoNombre-azure-redis-deploy
```

---

### SEMANA 7 — OWASP API Security Top 10 Audit Completo

**Objetivo:** Verificar y documentar compliance con los 10 controles OWASP API.

**Tareas del equipo:**
- [ ] Completar checklist `docs/owasp_api_audit.md` con evidencia de cada control:
  - API1: BOLA — verificar que no existe IDOR en ningún endpoint
  - API2: Broken Auth — verificar JWT, expiración, revocación
  - API3: Broken Object Property Level Auth — no se exponen campos sensibles
  - API4: Unrestricted Resource Consumption — rate limiting activo
  - API5: Broken Function Level Auth — admin routes protegidas
  - API6: Server-Side Request Forgery — no hay fetching de URLs de usuario
  - API7: Security Misconfiguration — headers, CORS, debug=False
  - API8: Lack of Protection from Automated Threats — CAPTCHA/rate limit
  - API9: Improper Inventory Management — no hay endpoints no documentados
  - API10: Unsafe Consumption of APIs — validar respuestas de APIs externas
- [ ] Solicitar escaneo a G5 (AuditAI) y documentar hallazgos

**Branch a crear:**
```bash
git checkout -b feature/S7-ApellidoNombre-owasp-api-audit
```

---

### SEMANA 8 ★ — EF: EVALUACIÓN FINAL (Proyecto 100%)

**Entregables OBLIGATORIOS:**
1. **Pull Request final** fusionado en `main`
2. **Demo completa** (20 minutos) en `finsecure.uqaisolutions.com.pe`
3. **Paper draft** — sección Methodology completa con arquitectura + fraud detection

**Rúbrica EF (100 puntos):**
| Criterio | Puntos |
|---|:---:|
| API completa desplegada en Azure | 25 |
| Fraud Detection ML con métricas (AUC-ROC, precision, recall) | 25 |
| OWASP API Top 10 compliance documentado | 20 |
| CI/CD + DevSecOps activo | 15 |
| Borrador paper Scopus Q1 | 10 |
| Presentación y defensa técnica | 5 |

---

## 🔧 Flujo de Trabajo GitHub (Obligatorio)

### 1. Fork del repositorio
```
GitHub → https://github.com/RubenCarty/PS-P2-SecureBank-API → Fork
```

### 2. Configurar tu fork localmente
```bash
git clone https://github.com/TU-USUARIO/PS-P2-SecureBank-API.git
cd PS-P2-SecureBank-API
git remote add upstream https://github.com/RubenCarty/PS-P2-SecureBank-API.git
git fetch upstream
```

### 3. Sincronizar antes de cada semana
```bash
git checkout main && git pull upstream main && git push origin main
```

### 4. Crear branch semanal
```bash
# Formato: feature/S{semana}-ApellidoNombre-descripcion
git checkout -b feature/S3-PerezJuan-transacciones-acid
```

### 5. PR hacia el repo del docente
```bash
git add app/transactions/router.py
git commit -m "feat(S3): add IDOR prevention and ACID transaction with rollback"
git push origin feature/S3-PerezJuan-transacciones-acid
# → GitHub PR → base: RubenCarty/PS-P2-SecureBank-API:main
```

---

## 📚 Repositorios de Referencia

### FastAPI y APIs Seguras
| Repositorio | Qué aprender |
|---|---|
| [tiangolo/full-stack-fastapi-template](https://github.com/tiangolo/full-stack-fastapi-template) | Estructura completa FastAPI con auth JWT, SQLAlchemy, Alembic |
| [fastapi/fastapi](https://github.com/fastapi/fastapi) | Documentación y ejemplos de seguridad OAuth2 + JWT |
| [mjhea0/awesome-fastapi](https://github.com/mjhea0/awesome-fastapi) | Curación de recursos FastAPI incluyendo seguridad |

### Fraude y Machine Learning para Fintech
| Repositorio | Qué aprender |
|---|---|
| [dmlc/xgboost](https://github.com/dmlc/xgboost) | XGBoost oficial con ejemplos de clasificación |
| [topics/fraud-detection](https://github.com/topics/fraud-detection) | Proyectos open source de fraud detection |
| [Kaggle Credit Card Fraud](https://github.com/georgymh/ml-fraud-detection) | Implementación con sklearn para detección de fraude |

### OWASP API Security
| Repositorio | Qué aprender |
|---|---|
| [OWASP/API-Security](https://github.com/OWASP/API-Security) | Repositorio oficial OWASP API Security Top 10 |
| [shieldfy/API-Security-Checklist](https://github.com/shieldfy/API-Security-Checklist) | Checklist completo de seguridad para APIs REST |

### Bancos y Fintech Open Source
| Repositorio | Qué aprender |
|---|---|
| [mifos/mifos-mobile](https://github.com/openMF/mifos-mobile) | Arquitectura de sistema bancario real |
| [stripe/stripe-python](https://github.com/stripe/stripe-python) | SDK de pagos con manejo seguro de errores |

---

## 🏗️ Estructura Completa del Proyecto

```
PS-P2-SecureBank-API/
│
├── 📄 README.md
├── 📄 CONTRIBUTING.md
├── 📄 requirements.txt
├── 📄 .env.example
│
├── 📁 .github/
│   ├── PULL_REQUEST_TEMPLATE.md
│   └── workflows/
│       ├── ci.yml
│       └── azure-deploy.yml
│
├── 📁 app/
│   ├── main.py                          ← FastAPI + middlewares
│   │
│   ├── 📁 auth/
│   │   ├── router.py                    ← POST /auth/login, /auth/refresh
│   │   ├── jwt_handler.py               ← Generar/validar/revocar JWT
│   │   └── dependencies.py              ← get_current_user dependency
│   │
│   ├── 📁 accounts/
│   │   ├── router.py                    ← CRUD de cuentas bancarias
│   │   ├── models.py                    ← Account, Balance
│   │   └── schemas.py                   ← Pydantic schemas
│   │
│   ├── 📁 transactions/
│   │   ├── router.py                    ← POST /transfer (anti-IDOR, ACID)
│   │   ├── models.py                    ← Transaction, AuditLog
│   │   └── schemas.py
│   │
│   ├── 📁 fraud/
│   │   ├── detector.py                  ← XGBoost + heurísticas (S5)
│   │   └── models.py                    ← FraudReport
│   │
│   └── 📁 audit/
│       ├── router.py                    ← GET /audit/logs
│       └── models.py                    ← AuditEntry
│
├── 📁 database/
│   └── schema.sql
│
├── 📁 docs/
│   ├── semana-01/arquitectura_financiera.md
│   ├── semana-01/threat_model.md
│   ├── semana-05/fraud_detection_design.md
│   ├── semana-07/owasp_api_audit.md
│   └── semana-08/EF_paper_draft.md
│
└── 📁 tests/
    ├── test_auth.py
    ├── test_transactions.py             ← Tests anti-IDOR, anti-tampering
    ├── test_fraud.py
    └── fixtures/generate_dataset.py    ← Dataset sintético para ML
```

---

## ⚡ Inicio Rápido (Local)

```bash
git clone https://github.com/TU-USUARIO/PS-P2-SecureBank-API.git
cd PS-P2-SecureBank-API
python -m venv venv && source venv/bin/activate
pip install -r requirements.txt
cp .env.example .env

uvicorn app.main:app --reload --port 8002
# → http://localhost:8002/api/docs (Swagger UI)

pytest tests/ -v
bandit -r app/ -ll
```

---

## 👥 Integrantes del Grupo G2

| # | Nombre | GitHub | Rol Técnico |
|:---:|---|---|---|
| 1 | [Completar] | [@usuario](https://github.com/usuario) | Líder / Auth JWT |
| 2 | [Completar] | [@usuario](https://github.com/usuario) | Transacciones + Cuentas |
| 3 | [Completar] | [@usuario](https://github.com/usuario) | Fraud Detection ML |
| 4 | [Completar] | [@usuario](https://github.com/usuario) | Azure Deploy + DevSecOps |
| 5 | [Completar] | [@usuario](https://github.com/usuario) | OWASP API Audit + Docs |

---

## 👨‍🏫 Contacto Docente

- **Docente:** Mg. Ruben Quispe Llacctarimay — [@RubenCarty](https://github.com/RubenCarty)
- **Repo del curso:** [DD281-Programacion-Segura-2026-1](https://github.com/RubenCarty/DD281-Programacion-Segura-2026-1)
- **Demo G2:** [finsecure.uqaisolutions.com.pe](https://finsecure.uqaisolutions.com.pe)

---

*Universidad Autónoma del Perú — DD281 Programación Segura — 2026-1*
*UQ AI SOLUTION COMPANY SAC — Ciclo VIII — Ingeniería de Sistemas*
