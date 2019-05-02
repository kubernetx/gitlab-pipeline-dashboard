# gitlab-pipeline-dashboard

### Inject access token as kubernetes secret
```bash
kubectl create secret generic gitlab-access-token --from-literal=api-token='<REDACTED>'
```