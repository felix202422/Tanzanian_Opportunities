[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)]
    [ValidateSet(
        "help",
        "db-up",
        "db-down",
        "compose-config",
        "backend-run",
        "backend-run-seed",
        "backend-test",
        "frontend-install",
        "frontend-dev",
        "frontend-lint",
        "frontend-typecheck",
        "frontend-format",
        "frontend-format-check",
        "frontend-build",
        "check"
    )]
    [string]$Task
)

$ErrorActionPreference = "Stop"
$RepositoryRoot = Split-Path -Parent $PSScriptRoot
$BackendDirectory = Join-Path $RepositoryRoot "backend"
$FrontendDirectory = Join-Path $RepositoryRoot "frontend"
$EnvironmentFile = Join-Path $RepositoryRoot ".env"

if (Test-Path -LiteralPath $EnvironmentFile) {
    foreach ($line in Get-Content -LiteralPath $EnvironmentFile) {
        if ($line -match '^\s*([A-Za-z_][A-Za-z0-9_]*)=(.*)$') {
            $name = $Matches[1]
            $value = $Matches[2].Trim()
            if ($value.Length -ge 2 -and (($value.StartsWith('"') -and $value.EndsWith('"')) -or ($value.StartsWith("'") -and $value.EndsWith("'")))) {
                $value = $value.Substring(1, $value.Length - 2)
            }
            Set-Item -Path "Env:$name" -Value $value
        }
    }
}
$EnvironmentFile = Join-Path $RepositoryRoot ".env"

if (Test-Path -LiteralPath $EnvironmentFile) {
    foreach ($line in Get-Content -LiteralPath $EnvironmentFile) {
        if ($line -match '^\s*([A-Za-z_][A-Za-z0-9_]*)=(.*)$') {
            $name = $Matches[1]
            $value = $Matches[2].Trim()
            if ($value.Length -ge 2 -and (($value.StartsWith('"') -and $value.EndsWith('"')) -or ($value.StartsWith("'") -and $value.EndsWith("'")))) {
                $value = $value.Substring(1, $value.Length - 2)
            }
            Set-Item -Path "Env:$name" -Value $value
        }
    }
}

function Invoke-CheckedCommand {
    param(
        [Parameter(Mandatory = $true)][string]$WorkingDirectory,
        [Parameter(Mandatory = $true)][string]$Executable,
        [Parameter(Mandatory = $true)][string[]]$Arguments
    )

    Push-Location -LiteralPath $WorkingDirectory
    try {
        & $Executable @Arguments
        if ($LASTEXITCODE -ne 0) {
            throw "$Executable failed with exit code $LASTEXITCODE"
        }
    }
    finally {
        Pop-Location
    }
}

switch ($Task) {
    "help" {
        Write-Output "Tasks: db-up, db-down, compose-config, backend-run, backend-run-seed, backend-test, frontend-install, frontend-dev, frontend-lint, frontend-typecheck, frontend-format, frontend-format-check, frontend-build, check"
    }
    "db-up" { Invoke-CheckedCommand $RepositoryRoot "docker" @("compose", "up", "-d", "postgres") }
    "db-down" { Invoke-CheckedCommand $RepositoryRoot "docker" @("compose", "down") }
    "compose-config" { Invoke-CheckedCommand $RepositoryRoot "docker" @("compose", "config") }
    "backend-run" { Invoke-CheckedCommand $BackendDirectory ".\mvnw.cmd" @("spring-boot:run") }
    "backend-run-seed" {
        $env:SPRING_PROFILES_ACTIVE = "seed"
        Invoke-CheckedCommand $BackendDirectory ".\mvnw.cmd" @("spring-boot:run")
    }
    "backend-test" { Invoke-CheckedCommand $BackendDirectory ".\mvnw.cmd" @("-B", "verify") }
    "frontend-install" { Invoke-CheckedCommand $FrontendDirectory "npm" @("ci") }
    "frontend-dev" { Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "dev") }
    "frontend-lint" { Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "lint") }
    "frontend-typecheck" { Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "typecheck") }
    "frontend-format" { Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "format") }
    "frontend-format-check" { Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "format:check") }
    "frontend-build" { Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "build") }
    "check" {
        Invoke-CheckedCommand $BackendDirectory ".\mvnw.cmd" @("-B", "verify")
        Invoke-CheckedCommand $FrontendDirectory "npm" @("ci")
        Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "lint")
        Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "typecheck")
        Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "format:check")
        Invoke-CheckedCommand $FrontendDirectory "npm" @("run", "build")
    }
}
