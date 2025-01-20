# SbomGen

[![license](https://img.shields.io/badge/License-Apache%202.0-red.svg)](LICENSE)
[![v1.0.0-macos](https://img.shields.io/badge/release-v1.0.0-green)](https://github.com/007vedant/SbomGen/releases/tag/v1.0.0-docker)
[![v1.0.0-docker](https://img.shields.io/badge/release-v1.0.0-blue)](https://github.com/007vedant/SbomGen/releases/tag/v1.0.0-macos)


**SbomGen** is a command-line application designed for generating Software Bill of Materials (SBOMs) and searching for packages within container images. It integrates seamlessly with [Syft](https://github.com/anchore/syft) to support popular SBOM formats like SPDX and CycloneDX.

---

## Features
- **SBOM Generation**: Create SBOMs for container images in SPDX or CycloneDX format.
- **Regex-Based Package Search**: Search for packages using regular expressions in the generated SBOMs.
- **Multiple Interfaces**:
    - **CLI Application**: Lightweight tool for local use.
    - **Docker Image**: Preconfigured for containerized environments.
- **Extensible Design**: Built with modularity in mind, allowing easy contribution and extension.

---

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Build Instructions](#build-instructions)
    - [Installation Instructions](#installation-instructions)
- [Usage](#usage)
    - [Running Application](#running-application)
    - [Command Examples](#command-examples)
- [Contributing](#contributing)

---

## Getting Started

### Prerequisites
- **Java**: JDK 21 or later.  
  Installation: [Get Java](https://www.oracle.com/java/technologies/downloads/#java21).
- **Docker**:  
  Installation: [Get Docker](https://docs.docker.com/engine/install/).
- **Syft CLI**: For running application directly from jar(not a prerequisite for docker image/macos binary because they are handling it automatically).
  
  Installation: [Get Syft CLI](https://github.com/anchore/syft?tab=readme-ov-file#installation).


### Build Instructions
1. Download the Project
   Clone the repository:
```bash
git clone https://github.com/007vedant/SbomGen.git
cd SbomGen
```

2. Build the Project
   Using Maven:
```bash
mvn clean package
```
This generates a JAR file at `target/SbomGen-1.0.0-jar-with-dependencies.jar`.

### Installation Instructions
#### MacOS Binary
**1. Download and Extract**
- Download the tar file from the Assets section below.
- Extract the tar file:
```
tar -xzvf sbomgen-macos-1.0.0.tar.gz
```
**2. Run the Installation Script**
- Navigate to the extracted folder:
```
cd sbomgen-macos
```
- Execute the installation script:
```
./install-sbomgen.sh
```
- The script will:
    - Install Syft via Homebrew (if not already installed).
    - Copy the SbomGen binary to `usr/local/bin`.
    - Set the necessary permissions.

#### Docker Image (compatible on all platforms)
**1. Download the image**
- Download the tar file from the Assets section below:
```
sbomgen-docker1.0.0.tar.gz
```
**2. Load the image**
- Load the image in your docker environment:
```
docker load < sbomgen-docker-1.0.0.tar.gz
```
**3. Verify the image**
- Check if the image is loaded:
```
docker images
```
You should see an entry for `sbomgen:1.0.0`.

### Usage
#### Running Application
**1. Built Jar**
```
java -jar target/SbomGen-1.0.0-jar-with-dependencies.jar -source <image> -format <CycloneDX/SPDX> -search <"regex"> -output <output file path>
```
**2. MacOS Binary**
```
sbomgen -source <image> -format <CycloneDX/SPDX> -search <"regex"> -output <output file path>
```
**3. Docker Image**
```
docker run --rm -v "$(pwd):/output" sbomgen:1.0.0 -source <image> -format <CycloneDX/SPDX> -search <"regex"> -output <output file path>
```
#### Command Examples
```
1. sbomgen -source alpine

2. sbomgen -source alpine -format SPDX

3. sbomgen -source alpine -format SPDX -search "libc.*"

4. sbomgen -source alpine -format SPDX -search "libc.*" -output output.json
```
---

## Contributing
We welcome contributions! To contribute:

### Fork the repository.
1. Create a new branch:
```
git checkout -b feature-branch-name
```
2. Commit your changes:
```
git commit -m "Add feature description"
```
3. Push to your fork:
```
git push origin feature-branch-name
```
4. Open a pull request on the main repository.
---
