# 🧩 MCUI – Minecraft UI Library

**MCUI** is a lightweight, extensible component-based UI library for Minecraft, designed to make in-game UI rendering easier and more modular.

## ✨ Features

- 📦 Component-based UI system
- 🖼️ Easy element rendering via `DefaultUIRender`
- ⏱️ Tick-based rendering lifecycle
- 🔌 Built-in support for spacing and layout with `ElementSpace`
- 🧱 Simple integration and extensibility

---

## 🚀 Getting Started

### 🧰 Requirements

- Java 21
- PaperMC 1.21+
- Maven

---

### 📦 Installation

Build the project using Maven:

```bash
mvn clean install
```

Add MCUI to your Maven project:

```xml
<dependency>
    <groupId>dev.lrxh</groupId>
    <artifactId>MCUI</artifactId>
    <version>1.0.0</version>
</dependency>
```

> ⚠️ **Important:** You must declare `MCUI` as a dependency in your `plugin.yml` to ensure your plugin loads after MCUI.  
> Example:
> ```yaml
> depend: [MCUI]
> ```

---

## 🛠️ Usage

### 1. Create a UI Component

```java
public class UIComponentExample extends UIComponent {
    private final Element element;

    public UIComponentExample() {
        // HEIGHT and ASCENT are optional spacing parameters
        element = register("image_0.png", 8, 0);
        // The image must be located inside MCUI/assets        
        load();
    }

    @Override
    public void tick(Player player) {
        DefaultUIRender.ACTION_BAR.render(player,
                ElementSpace.BACKSPACE_48.createElement(),
                element,
                ElementSpace.BACKSPACE_1.createElement(),
                element,
                ElementSpace.BACKSPACE_1.createElement(),
                element
        );
    }
}
```

### 2. Register Your Component

```java
UIComponent uiComponent = MCUI.INSTANCE.getComponentManager().registerComponent(new UIComponentExample());
```

### 3. Show/Hide UI for a Player

To display or remove the UI for a specific player:

```java
// Show UI
uiComponent.addViewer(player);     // or addViewer(player.getUniqueId());

// Hide UI
uiComponent.removeViewer(player);  // or removeViewer(player.getUniqueId());
```

### In-Game Example
![image](https://github.com/user-attachments/assets/f6573c9a-1053-4726-8803-06253b6b8128)

---

## 📜 License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 🙌 Acknowledgements

- [PaperMC](https://papermc.io/)
- [Netty](https://netty.io/)
- [MCBrawls Inject Framework](https://github.com/MCBrawls/Inject)
