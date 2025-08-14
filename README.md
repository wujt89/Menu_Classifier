## Menu_Classifier

An Android application that uses on‑device text recognition to extract ingredients from photos of product labels or menus and classifies them for gluten presence and vegetarian suitability. Users can scan from the camera or gallery, view highlighted results, and maintain simple local lists of products by category.

### Key Features
- **On‑device OCR**: Extract text using Google ML Kit Text Recognition (Latin) with no network requirement.
- **Two scanning modes**:
  - **Product**: Classifies a single product as Gluten Free / Not Gluten Free and Vegetarian / Not Vegetarian.
  - **Menu**: Parses menu lines and highlights potential allergens or meat; adds inline tags like “GF” and “V”.
- **Image sources**: Pick from gallery or capture via camera.
- **Highlighted results**: Keywords for gluten and meat are color‑highlighted in the recognized text.
- **Local storage**: Save product names into local SQLite tables by category and review them in a dedicated screen.

### Tech Stack
- **Language**: Kotlin
- **Minimum SDK**: 21
- **Target/Compile SDK**: 32
- **Build**: Android Gradle Plugin 7.1.2, Kotlin 1.5.30, Data Binding enabled
- **ML**: Google ML Kit Text Recognition (`play-services-mlkit-text-recognition:18.0.0`)
- **UI**: AndroidX AppCompat, Material Components, ConstraintLayout
- **Storage**: SQLite via `SQLiteOpenHelper`

### App Structure (high‑level)
- Activities:
  - `firstScreen`: Entry screen, handles runtime permissions and navigation.
  - `ChooseMenu`: Lets the user choose between Product and Menu scanning flows.
  - `productScreen`: Scan a product and classify Gluten/Vegetarian; allows saving to local DB.
  - `menuScreen`: Scan a menu image, show image/text toggle, highlight and tag lines.
  - `myProducts`: Lists locally saved items by category (Gluten Free, Not Gluten Free, Vegetarian, Not Vegetarian).
- ViewModels:
  - `ChooseMenuViewModel`, `productScreenModelView`, `menuScreenModelView`, `myProductsViewModel`.
- Local Databases:
  - `DataBase` (tables: `PRODUCTS`, `NOTGLUTENFREE`), `DBVegetarian` (`VEGETARIAN`), `DBNotVegetarian` (`NOTVEGETARIAN`), `DBNotGluten` (`NOTGLUTENFREE`).

### Classification Logic (summary)
- The recognized text is split and matched against simple keyword lists.
- Example keyword lists (case‑insensitive):
  - Gluten indicators: `bread`, `flour`, `pasta`, `wheat`.
  - Meat indicators: `steak`, `chicken`, `salmon`, `prawns`, `becon`, `gelatine`, `beef`.
- Matches are highlighted in the rendered results and determine the labels:
  - Product flow: “Gluten Free” / “NOT Gluten Free”, “Vegetarian” / “NOT Vegetarian”.
  - Menu flow: Adds inline “GF” (no gluten keywords found) and “V” (no meat keywords found) tags per line.

## Getting Started

### Prerequisites
- Android Studio (Arctic Fox / Bumblebee or newer)
- Android SDK Platform 32 installed
- Device or emulator running Android 5.0 (API 21) or newer

### Build & Run
1. Open the project in Android Studio.
2. Let Gradle sync (AGP 7.1.2, Kotlin 1.5.30).
3. Build and run on a device/emulator.
4. On first launch, grant Camera and Storage permissions when prompted.

### Permissions
Declared in `AndroidManifest.xml`:
- `CAMERA`
- `READ_EXTERNAL_STORAGE`
- `WRITE_EXTERNAL_STORAGE` (legacy; used for camera output on older Android versions)

### Usage
- From the entry screen, choose:
  - **Check** → `ChooseMenu`, then select:
    - **Product**: Import or capture an image. The app recognizes text, highlights keywords, and displays classification. Optionally enter a product name and tap Add to store it locally in the appropriate table.
    - **Menu**: Import or capture an image. Toggle between the image and recognized text views. Lines are highlighted and tagged with GF/V as applicable.
  - **My Products**: Browse saved entries grouped by category.

## Data & Privacy
- Text recognition is performed on‑device via ML Kit.
- No network calls are required for OCR. All saved data is stored locally in SQLite databases on the device.

## Known Limitations
- Keyword‑based matching: Results depend on a fixed, small English keyword list and may not capture all cases or languages.
- Layout sensitivity: OCR output format can vary by image quality and orientation; the app applies a fixed 90° rotation which may not fit all photos.
- Storage permissions: Uses legacy external storage permissions for broader device compatibility.

## Potential Improvements
- Expand multilingual and domain‑specific keyword sets; allow in‑app editing of keyword lists.
- Replace keyword matching with a more robust ingredient parser.
- Improve orientation handling and add auto‑rotation/deskew.
- Migrate to scoped storage and modern camera APIs (e.g., CameraX).
- Persist products using Room with a single schema and DAO layer.

## Dependencies (excerpt)
```gradle
implementation 'com.google.android.gms:play-services-mlkit-text-recognition:18.0.0'
implementation 'com.google.mlkit:vision-common:16.5.0'
implementation 'androidx.core:core-ktx:1.7.0'
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'com.google.android.material:material:1.5.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
```

## Acknowledgements
- Text recognition powered by Google ML Kit Text Recognition. See the official docs: [ML Kit Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/v2)
