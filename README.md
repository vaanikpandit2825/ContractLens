<div align="center">

<!-- ANIMATED TYPING BANNER -->
<a href="#">
  <img src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=800&size=48&duration=3000&pause=1000&color=00D4FF&center=true&vCenter=true&width=700&height=90&lines=ContractLens" alt="ContractLens" />
</a>

<a href="#">
  <img src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=500&size=20&duration=3500&pause=800&color=7C85FF&center=true&vCenter=true&width=700&height=45&lines=AI-Powered+Contract+Risk+Analyzer;Upload.+Extract.+Analyze.+Understand.;Built+for+engineers.+Designed+for+humans." alt="Subtitle" />
</a>

<br/><br/>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-SDK%2034-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Gemini AI](https://img.shields.io/badge/Gemini_AI-Powered-4285F4?style=for-the-badge&logo=google&logoColor=white)](https://ai.google.dev)
[![Material Design](https://img.shields.io/badge/Material_Design-3-757575?style=for-the-badge&logo=material-design&logoColor=white)](https://m3.material.io)

[![License](https://img.shields.io/badge/License-MIT-00D4FF?style=for-the-badge)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active_Development-00FF88?style=for-the-badge)]()
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-7C85FF?style=for-the-badge&logo=github)](CONTRIBUTING.md)
[![Platform](https://img.shields.io/badge/Platform-Android_API_26+-3DDC84?style=for-the-badge&logo=android&logoColor=white)]()

</div>

<br/>

---

## `01` &nbsp; What Is ContractLens?

> **ContractLens** turns the black box of legal contracts into something you can actually understand — in seconds.

Most people sign contracts without fully reading them. Not because they're careless — because legal language is dense, obfuscated, and deliberately opaque. **ContractLens fixes that.**

Upload any PDF contract. ContractLens extracts the text, fires it through **Gemini 1.5 Pro**, and surfaces the risky clauses — explained in plain English, scored by severity, and delivered inside a clean Android interface.

It's not a PDF reader. It's a **risk engine**.

<br/>

---

## `02` &nbsp; Tech Stack

<br/>

<div align="center">

| Layer | Technology | Purpose |
|:---|:---|:---|
| **Language** | ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white) Kotlin 2.0 | Primary application language |
| **Platform** | ![Android](https://img.shields.io/badge/Android_SDK_34-3DDC84?style=flat-square&logo=android&logoColor=white) Android SDK 34 | Mobile runtime target |
| **UI Layer** | ![XML](https://img.shields.io/badge/XML_Layouts-F5820D?style=flat-square&logo=android&logoColor=white) XML + Material 3 | View inflation and component system |
| **AI Engine** | ![Gemini](https://img.shields.io/badge/Gemini_1.5_Pro-4285F4?style=flat-square&logo=google&logoColor=white) Gemini API | Contract analysis and risk extraction |
| **PDF Engine** | ![PDFBox](https://img.shields.io/badge/PDFBox_Android-CC0000?style=flat-square&logo=apache&logoColor=white) Apache PDFBox | Text extraction from PDF contracts |
| **Async** | ![Coroutines](https://img.shields.io/badge/Coroutines-7F52FF?style=flat-square&logo=kotlin&logoColor=white) Kotlin Coroutines | Non-blocking I/O and lifecycle-safe ops |
| **Design** | ![Material](https://img.shields.io/badge/Material_Design_3-757575?style=flat-square&logo=material-design&logoColor=white) Material Design 3 | Component tokens and theming |

</div>

<br/>

---

## `03` &nbsp; Feature Set

<br/>

<table>
<tr>
<td width="33%" valign="top">

### 📄 &nbsp; PDF Upload
Upload contracts directly from device storage. Any legal PDF, any size — no preprocessing, no file conversion required.

`PDFBox Android`

</td>
<td width="33%" valign="top">

### &nbsp; AI Risk Detection
Gemini 1.5 Pro identifies hidden liabilities, one-sided clauses, and legal traps in real time with zero manual review.

`Gemini 1.5 Pro`

</td>
<td width="33%" valign="top">

### &nbsp; Risk Scoring
Every clause rated **Low / Medium / High** severity with color-coded visual indicators and justification per flag.

`Per-clause grading`

</td>
</tr>
<tr>
<td width="33%" valign="top">

### &nbsp; Contract Analysis
Full clause-by-clause breakdown with section-level context preserved. No hallucinated summaries — grounded in your document.

`Deep parsing`

</td>
<td width="33%" valign="top">

### 💬 &nbsp; Plain English Output
Zero legal jargon. Every flagged risk explained in language a non-lawyer understands immediately. No law degree required.

`Human-readable`

</td>
<td width="33%" valign="top">

### 🖥️ &nbsp; Multi-Screen UI
Home → Upload → Analysis → Result. Clean navigation flow with persistent state and Material Design 3 throughout.

`Material Design 3`

</td>
</tr>
</table>

<br/>

---

## `04` &nbsp; Architecture Flow

<br/>

```mermaid
flowchart LR
    A([📄 PDF Upload]) --> B[PDFBox Android\nText Extraction]
    B --> C[Coroutine Scope\nAsync Layer]
    C --> D([ Gemini 1.5 Pro\nAI Engine])
    D --> E[Risk Analysis\nClause Scoring]
    E --> F([ Result Screen])

    style A fill:#0d2137,color:#7ec8f7,stroke:#1a4a7a,stroke-width:1.5px
    style B fill:#1a1a2e,color:#a78bfa,stroke:#3d2d6b,stroke-width:1px
    style C fill:#1a1a2e,color:#94a3b8,stroke:#2d3748,stroke-width:1px
    style D fill:#0d1f3c,color:#60a5fa,stroke:#1e40af,stroke-width:1.5px
    style E fill:#1a1a2e,color:#94a3b8,stroke:#2d3748,stroke-width:1px
    style F fill:#0d2b1f,color:#6ee7b7,stroke:#065f46,stroke-width:1.5px
```

<br/>

<div align="center">

| Step | Component | What Happens |
|:---:|:---|:---|
| `01` | **PDF Upload** | User selects contract from device storage via file picker intent |
| `02` | **PDFBox Extraction** | Apache PDFBox parses PDF binary, extracts raw text preserving structure |
| `03` | **Coroutine Async Layer** | Extraction and API call dispatched on `IO` dispatcher, off main thread |
| `04` | **Gemini 1.5 Pro** | Structured prompt sent with full contract text, model returns clause analysis |
| `05` | **Risk Analysis** | Response parsed into clause objects with severity scores and explanations |
| `06` | **Result Screen** | Flagged clauses rendered in UI with risk levels, color coding, and summaries |

</div>

<br/>

---

## `05` &nbsp; Installation

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/ContractLens.git
cd ContractLens

# 2. Open in Android Studio (Hedgehog or later)
# File → Open → select the ContractLens folder

# 3. Add your Gemini API key to local.properties
echo "GEMINI_API_KEY=your_key_here" >> local.properties

# 4. Sync Gradle
# Android Studio → File → Sync Project with Gradle Files

# 5. Run on device or emulator (API 26+ required)
# Run → Run 'app'  or  Shift+F10
```

<br/>

<div align="center">

| Requirement | Minimum | Recommended |
|:---|:---|:---|
| Android Studio | Hedgehog 2023.1.1 | Ladybug 2024.2.1+ |
| JDK | 17 | 17 |
| Android SDK | API 26 (Oreo) | API 34 (Android 14) |
| Gemini API Key | Required | [Get one free →](https://aistudio.google.com) |

</div>

<br/>

---

## `06` &nbsp; Engineering Challenges Solved

Real problems solved during development — not tutorials, not theory.

<br/>

<table>
<tr>
<td width="6%" valign="top"><code>01</code></td>
<td valign="top">

**Gradle API Key Injection**
Securely injecting the Gemini API key from `local.properties` into the build via `buildConfigField` — keeping secrets out of source control without sacrificing runtime access. Zero hardcoded credentials in any committed file.

</td>
</tr>
<tr>
<td valign="top"><code>02</code></td>
<td valign="top">

**Prompt Engineering for Legal Context**
Getting Gemini to produce consistent, structured output across wildly different contract types — NDAs, employment agreements, SaaS terms — required iterative prompt refinement with explicit output schema definitions and clause-boundary instructions.

</td>
</tr>
<tr>
<td valign="top"><code>03</code></td>
<td valign="top">

**Android Async Without Leaks**
Managing long-running PDF parsing and API calls without blocking the main thread. Coroutine scopes tied to ViewModel lifecycle prevent memory leaks and zombie operations when the user navigates away mid-analysis.

</td>
</tr>
<tr>
<td valign="top"><code>04</code></td>
<td valign="top">

**PDF Text Extraction at Scale**
PDFBox on Android has constraints the desktop version doesn't. Handled encoding edge cases, multi-column layouts, and contracts with embedded tables that naive extractors mangle — preserving clause structure for downstream AI parsing.

</td>
</tr>
<tr>
<td valign="top"><code>05</code></td>
<td valign="top">

**API Quota Debugging**
Gemini free-tier quota limits hit fast during development. Implemented proper error state handling, descriptive user-facing feedback, and identified the specific request patterns that triggered rate limiting early in the cycle.

</td>
</tr>
<tr>
<td valign="top"><code>06</code></td>
<td valign="top">

**Structured Output from Unstructured Input**
Legal documents have no standard format. Built a prompt pipeline that forces clause-level specificity from Gemini regardless of document structure — consistently returning parseable, predictable output across wildly different contract formats.

</td>
</tr>
</table>

<br/>

---

## `07` &nbsp; Roadmap

<br/>

<div align="center">

| Status | Feature | Notes |
|:---:|:---|:---|
| ✅ | PDF upload and text extraction | Shipped |
| ✅ | Gemini AI integration | Shipped |
| ✅ | Risk identification and plain English output | Shipped |
| ✅ | Multi-screen Android UI | Shipped |
| ✅ | Error handling and loading states | Shipped |
| ⬜ | Structured clause cards with expandable detail | In design |
| ⬜ | Retry system with exponential backoff | Planned |
| ⬜ | Analysis history — local persistence via Room | Planned |
| ⬜ | Enhanced UI micro-animations and transitions | Planned |
| ⬜ | Cloud sync for cross-device analysis access | Future |
| ⬜ | Premium legal insight tier — jurisdiction-aware | Future |
| ⬜ | Clause comparison across multiple contracts | Future |
| ⬜ | Export risk report as shareable PDF | Future |

</div>

<br/>

---

## `08` &nbsp; Contributing

Contributions are welcome — but read this first.

```bash
# 1. Fork the repository on GitHub

# 2. Create a focused feature branch
git checkout -b feature/your-feature-name

# 3. Commit with intent — what changed and why
git commit -m "feat: add exponential backoff for Gemini API retries"

# 4. Push your branch
git push origin feature/your-feature-name

# 5. Open a Pull Request with context
# Describe what you changed, why, and how you tested it
```

> **Before opening a PR:** Bug fix? Reference the issue number. New feature? Open a discussion first. PRs that add dependencies without justification will be closed. Small improvements — typos, docs, tests — are always welcome.

<br/>

---

## `09` &nbsp; License

```
MIT License — use it, build on it, ship it.
Attribution appreciated. Credit optional but respected.
```

See [`LICENSE`](LICENSE) for full terms.

<br/>

---

<br/>

<div align="center">

<a href="#">
  <img src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=600&size=15&duration=4000&pause=600&color=00D4FF&center=true&vCenter=true&width=650&height=35&lines=Contracts+are+written+by+lawyers+for+lawyers.;ContractLens+changes+the+equation." alt="Footer tagline" />
</a>

<br/><br/>

[![GitHub Stars](https://img.shields.io/github/stars/vaanikpandit2825/ContractLens?style=for-the-badge&color=00D4FF&labelColor=0D1117)](https://github.com/yourusername/ContractLens/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/vaanikpandit2825/ContractLens?style=for-the-badge&color=7C85FF&labelColor=0D1117)](https://github.com/yourusername/ContractLens/network/members)
[![GitHub Issues](https://img.shields.io/github/issues/vaanikpandit2825/ContractLens?style=for-the-badge&color=FF6B6B&labelColor=0D1117)](https://github.com/yourusername/ContractLens/issues)

<br/>

**Built with** &nbsp;
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Gemini](https://img.shields.io/badge/Gemini_AI-4285F4?style=flat-square&logo=google&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![PDFBox](https://img.shields.io/badge/PDFBox-CC0000?style=flat-square&logo=apache&logoColor=white)

<br/>

*If ContractLens helped you or impressed you — drop a ⭐. It costs nothing and means something.*

</div>
