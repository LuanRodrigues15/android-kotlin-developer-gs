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

<table>
  <tr>
    <td align="center">
      <strong>Splash Screen</strong><br>
      <img src="https://github.com/user-attachments/assets/ae0a10b9-9ed6-450e-883f-d9fc35534ffd" width="220"/>
    </td>
    <td align="center">
      <strong>Onboarding - Página 1</strong><br>
      <img src="https://github.com/user-attachments/assets/9f580be0-208b-48d8-8f4e-ff591af3058f" width="220"/>
    </td>
    <td align="center">
      <strong>Onboarding - Página 2</strong><br>
      <img src="https://github.com/user-attachments/assets/35bdac67-81db-44fb-ad7c-ffe9e164df20" width="220"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <strong>Onboarding - Página 3</strong><br>
      <img src="https://github.com/user-attachments/assets/a8585f88-d4a7-41f7-921f-b3cd8278699f" width="220"/>
    </td>
    <td align="center">
      <strong>Home - Estado Inicial</strong><br>
      <img src="https://github.com/user-attachments/assets/0367275f-a982-4137-b746-a6f7c759aab3" width="220"/>
    </td>
    <td align="center">
      <strong>Home - Dados Carregados</strong><br>
      <img src="https://github.com/user-attachments/assets/8afdaebf-1bbe-4722-8ae3-63b7f8fb2089" width="220"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <strong>Análise Regional</strong><br>
      <img src="https://github.com/user-attachments/assets/86000986-11e8-4cf1-8321-e7fa0d1084d5" width="220"/>
    </td>
    <td align="center">
      <strong>Modo Técnico</strong><br>
      <img src="https://github.com/user-attachments/assets/38407b8c-dadf-4cde-84f5-f70df94b7b08" width="220"/>
    </td>
    <td align="center">
      <strong>Explorar Culturas</strong><br>
      <img src="https://github.com/user-attachments/assets/058894fd-da1e-403d-9591-cf3153b88f45" width="220"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <strong>Busca Ativa</strong><br>
      <img src="https://github.com/user-attachments/assets/253bd41e-6ee9-4709-acbf-3c53b711e1dc" width="220"/>
    </td>
    <td align="center">
      <strong>Detalhe da Cultura</strong><br>
      <img src="https://github.com/user-attachments/assets/85b21348-c34a-4d00-a605-4714882c00c8" width="220"/>
    </td>
    <td align="center">
      <strong>Favoritos (Análises e Culturas)</strong><br>
      <img src="https://github.com/user-attachments/assets/05734953-6f5e-4b72-bf8a-2d1c45fda331" width="220"/>
      <br><br>
      <img src="https://github.com/user-attachments/assets/649430d4-a3d7-420f-ae16-2cdda0dacca7" width="220"/>
    </td>
  </tr>
</table>


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
