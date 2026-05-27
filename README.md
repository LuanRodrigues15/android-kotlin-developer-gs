# AgroSat

Plataforma de consultoria agrícola automatizada baseada em dados de satélite. O AgroSat consome dados meteorológicos reais da API Open-Meteo para recomendar culturas e criações para regiões agrícolas do Brasil, traduzindo informações técnicas em linguagem acessível para produtores rurais de qualquer porte.

---

## Tema da Global Solution

**Space Connect — Tecnologia Espacial Aplicada a Desafios Reais**

A agropecuária brasileira depende criticamente de informações climáticas precisas. O AgroSat conecta dados de sensoriamento remoto e satélites meteorológicos diretamente ao produtor rural, reduzindo perdas causadas por decisões sem suporte técnico.

---

## Fluxo de Telas

```
Splash Screen
    └── Onboarding (3 páginas, exibido apenas na primeira abertura)
            └── Home Screen (seleção de região + dados climáticos)
                    ├── Análise Regional (dados detalhados + ranking de culturas)
                    └── Explorar por Cultura (lista com busca)
                            └── Detalhe da Cultura (condições ideais + score regional)

Home Screen
    └── Favoritos (análises e culturas salvas)
```

---

## Prints das Telas

<!-- INSERIR PRINT: Splash Screen -->

<!-- INSERIR PRINT: Onboarding - Página 1 (Inteligência do Espaço) -->

<!-- INSERIR PRINT: Onboarding - Página 2 (Para Todo Produtor) -->

<!-- INSERIR PRINT: Onboarding - Página 3 (Sua Terra, Seu Potencial) -->

<!-- INSERIR PRINT: Home Screen - Estado inicial (sem região selecionada) -->

<!-- INSERIR PRINT: Home Screen - Com dados climáticos carregados -->

<!-- INSERIR PRINT: Análise Regional - Visão Geral -->

<!-- INSERIR PRINT: Análise Regional - Modo Técnico -->

<!-- INSERIR PRINT: Explorar por Cultura - Grid de culturas -->

<!-- INSERIR PRINT: Explorar por Cultura - Busca ativa -->

<!-- INSERIR PRINT: Detalhe da Cultura (ex: Soja) -->

<!-- INSERIR PRINT: Favoritos - Aba Análises -->

<!-- INSERIR PRINT: Favoritos - Aba Culturas -->

---

## API Utilizada

**Open-Meteo** — API meteorológica pública, gratuita e sem autenticação.

- Base URL: `https://api.open-meteo.com/v1/`
- Endpoint: `GET /forecast`
- Parâmetros utilizados:
  - `latitude`, `longitude` — coordenadas da região selecionada
  - `current` — `temperature_2m`, `precipitation`, `weathercode`, `windspeed_10m`
  - `daily` — `temperature_2m_max`, `temperature_2m_min`, `precipitation_sum`, `weathercode`
  - `timezone` — `America/Sao_Paulo`
  - `forecast_days` — `7`

Regiões cobertas:
| Região | Estado | Latitude | Longitude |
|--------|--------|----------|-----------|
| Sorriso | MT | -12.5444 | -55.7208 |
| Corumbá | MS | -19.0078 | -57.6531 |
| Ribeirão Preto | SP | -21.1775 | -47.8103 |

---

## Arquitetura

O projeto aplica **Clean Architecture** com três camadas bem delimitadas:

### `data/`
- `remote/api/` — Interface Retrofit (`OpenMeteoApi`)
- `remote/dto/` — DTOs de resposta da API (`WeatherResponseDto`, `CurrentWeatherDto`, `DailyWeatherDto`)
- `remote/` — DataSource abstrato e implementação (`WeatherRemoteDataSource`, `WeatherRemoteDataSourceImpl`)
- `repository/` — Implementação do repositório (`WeatherRepositoryImpl`)
- `model/` — Modelos de dados internos e mapeamento DTO→domínio (`WeatherDataModel`, `RegionData`, `CultureData`)
- `preferences/` — Gerenciamento de DataStore (`DataStoreManager`)

### `domain/`
- `model/` — Entidades de domínio (`WeatherData`, `RegionInfo`, `CultureRanking`, `RegionAnalysis`)
- `repository/` — Interface do repositório (`WeatherRepository`)
- `usecase/` — Casos de uso (`GetWeatherUseCase`, `GetCultureRankingUseCase`, `GetRegionAnalysisUseCase`)
- `common/` — Wrapper de resultado (`Resource`)

### `presentation/`
- `navigation/` — Rotas e NavHost (`AppRoutes`, `AppNavHost`)
- `screens/` — Telas: Splash, Onboarding, Home, Analysis, Cultures, Detail, Favorites
- `viewmodel/` — ViewModels com StateFlow: `HomeViewModel`, `AnalysisViewModel`, `CulturesViewModel`, `FavoritesViewModel`, `SplashViewModel`, `OnboardingViewModel`
- `components/` — Composables reutilizáveis: `WeatherCard`, `CultureCard`, `RankingItem`, `RegionSelector`, `LoadingIndicator`, `ErrorMessage`
- `common/` — Estado de UI (`UiState`)

### Injeção de Dependência
Koin com módulos separados por camada: `networkModule`, `dataModule`, `domainModule`, `presentationModule`.

---

## Tecnologias Utilizadas

| Tecnologia | Uso |
|------------|-----|
| Kotlin + Jetpack Compose | Linguagem e UI declarativa |
| Navigation Compose | Navegação entre telas |
| Retrofit + OkHttp | Consumo da API REST |
| kotlinx.serialization | Deserialização de JSON |
| Koin | Injeção de dependência |
| DataStore Preferences | Persistência de onboarding |
| Coil | Carregamento de imagens |
| Coroutines + StateFlow | Programação assíncrona e estados reativos |
| Core SplashScreen | API de splash nativa do Android |

---

## Vídeo Pitch

<!-- INSERIR LINK DO YOUTUBE AQUI -->

---

## Desenvolvedor(es)

| Nome | RM |
|------|----|
| Luan Rodrigues Pupo Serra | 552737 |
| Mateus Henrique Bessornia da Silva | 552730 |
| João Henrique Garcia de Santana Cruz | 553841 |

**FIAP — Global Solution 2026.1**
