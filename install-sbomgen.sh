#!/bin/bash

TOOL_NAME="sbomgen"
INSTALL_DIR="/usr/local/bin"
BINARY_NAME="sbomgen"

command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Installing Syft using Homebrew if not present already
echo "Checking for Syft..."
if command_exists syft; then
    echo "Syft is already installed."
else
    echo "Syft is not installed. Installing via Homebrew..."
    if command_exists brew; then
        brew install syft
    else
        echo "Homebrew is not installed. Please install Homebrew and run this script again."
        exit 1
    fi
fi

# Copying the binary to the installation directory
echo "Installing $TOOL_NAME..."
if [ -f "./$BINARY_NAME" ]; then
    chmod +x "./$BINARY_NAME"
    sudo cp "./$BINARY_NAME" "$INSTALL_DIR/$TOOL_NAME"
    echo "$TOOL_NAME has been installed to $INSTALL_DIR."
else
    echo "Error: Binary file $BINARY_NAME not found in the current directory."
    exit 1
fi

# Verifying installation
if command_exists $TOOL_NAME; then
    echo "$TOOL_NAME installation completed successfully!"
    echo "Run '$TOOL_NAME -source <image>' to get started."
else
    echo "Installation failed. Please check the script and try again."
    exit 1
fi
