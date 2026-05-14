import type { Party, PartyDraft } from '../domain/party'

export async function listParties(): Promise<Party[]> {
  const response = await fetch('/api/parties')
  return (await response.json()) as Party[]
}

export async function createParty(draft: PartyDraft): Promise<Party> {
  const response = await fetch('/api/parties', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(draft),
  })
  if (!response.ok) throw new Error('Could not create party.')
  return (await response.json()) as Party
}

export async function updateParty(partyId: number, draft: PartyDraft): Promise<Party> {
  const response = await fetch(`/api/parties/${partyId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(draft),
  })
  if (!response.ok) throw new Error('Could not update party.')
  return (await response.json()) as Party
}

export async function deleteParty(partyId: number): Promise<void> {
  const response = await fetch(`/api/parties/${partyId}`, { method: 'DELETE' })
  if (!response.ok) throw new Error('Could not delete party.')
}
